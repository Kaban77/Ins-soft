package ru.demidov.insSoft.policy;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PolicyRepository {

	private final JdbcTemplate jdbctemplate;

	private static final String SQL_GET_POLICY_ID = "select policies.pkg_utilities.get_id('t_policy') from dual";
	private static final String SQL_SELECT_BY_ID = "select * from policies.v_policy_full where policy_id = :policyId";
	private static final String SQL_SELECT_LIST = """
			select *
			from policies.v_policy_full
			where upper(policy_number) like '%' || :policyNumber || '%'
			""";
	private static final String SQL_INSERT = """
			insert into policies.t_policy (policy_id, policy_number, begin_date, end_date, insurant_id, state, premium)
			values (:policyId, :policyNo, sysdate, trunc(sysdate) + 364, :insurantId, 0, :premium)
			""";
	private static final String SQL_UPDATE = """
			update policies.t_policy
			set insurant_id = :insurantId,
			    state = :state,
			    premium = :premium
			where policy_id = :policy_id
			""";

	public PolicyRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public Integer getPolicyId() {
		return jdbctemplate.queryForObject(SQL_GET_POLICY_ID, Integer.class);
	}

	public void insert(Policy policy, BigDecimal premium) {
		jdbctemplate.update(SQL_INSERT, policy.getPolicyId(), "EEE" + policy.getPolicyId(), policy.getInsurant().getId(), premium);
	}

	public Policy select(Integer policyId) {
		return jdbctemplate.queryForObject(SQL_SELECT_BY_ID, Policy::new, policyId);
	}

	public List<Policy> select(PolicyForSearch policy) {
		var sql = new StringBuilder(SQL_SELECT_LIST);
		var args = new ArrayList<String>();

		args.add(policy.getPolicyNumber().toUpperCase().trim());
		if (tryParseDate(policy.getStartPeriod()) != false) {
			sql.append(" and begin_date >= to_date(:beginDate, 'DD.MM.YYYY')");
			args.add(policy.getStartPeriod());
		}
		if (tryParseDate(policy.getEndPeriod()) != false) {
			sql.append(" and begin_date <= to_date(:endDate, 'DD.MM.YYYY')");
			args.add(policy.getEndPeriod());
		}
		return jdbctemplate.query(sql.toString(), Policy::new, args.toArray());
	}

	public void update(Policy policy) {
		jdbctemplate.update(SQL_UPDATE, policy.getInsurant().getId(), policy.getPolicyStateId(), policy.getCoeff().getPremium(),
				policy.getPolicyId());
	}

	private boolean tryParseDate(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			format.parse(date);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}
}
