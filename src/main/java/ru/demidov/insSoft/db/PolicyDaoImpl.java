package ru.demidov.insSoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import ru.demidov.insSoft.controllers.LoginController;
import ru.demidov.insSoft.interfaces.PolicyDao;
import ru.demidov.insSoft.objects.Policy;

public class PolicyDaoImpl implements PolicyDao {

	private JdbcTemplate jdbctemplate;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	public PolicyDaoImpl() {

	}

	public PolicyDaoImpl(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Policy> findPoliciesByParam(Policy policy) {

		String sql = "select * from policies.v_policy_info where upper(policy_number) like :policyNumber";
		Date beginDate = null, endDate = null;
		ArrayList<String> args = new ArrayList<String>();

		args.add("%" + policy.getPolicyNumber().toUpperCase().trim() + "%");

		try {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			beginDate = format.parse(policy.getBeginDate());
			endDate = format.parse(policy.getEndDate());

		} catch (Exception e) {
		}

		if (beginDate != null) {
			sql += " and begin_date >= to_date(:beginDate, 'DD.MM.YYYY')";
			args.add(policy.getBeginDate());
		}

		if (endDate != null) {
			sql += " and begin_date <= to_date(:endDate, 'DD.MM.YYYY')";
			args.add(policy.getEndDate());
		}

		try {
			return jdbctemplate.query(sql, args.toArray(), new PolicyRowMapper());
		} catch (

		Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Policy findPolicy(int policyId) {

		String sql = "select * from policies.policy where policy_id = :policyId";
		try {
			return jdbctemplate.queryForObject(sql, new Object[] { policyId }, new PolicyRowMapper());
		} catch (IncorrectResultSizeDataAccessException dae) {
			return null;
		}
	}

	@Override
	public Policy findPolicy(String policyNumber) {

		String sql = "select * from policies.policy where policy_number = :policy_number";
		try {
			return jdbctemplate.queryForObject(sql, new Object[] { policyNumber }, new PolicyRowMapper());
		} catch (IncorrectResultSizeDataAccessException dae) {
			return null;
		}
	}

	private static final class PolicyRowMapper implements RowMapper<Policy> {

		@Override
		public Policy mapRow(ResultSet rs, int rowNum) throws SQLException {
			Policy policy = new Policy();

			policy.setBeginDate(rs.getDate("begin_date").toString());
			policy.setEndDate(rs.getDate("end_date").toString());
			policy.setPolicyId(rs.getInt("policy_id"));
			policy.setPolicyNumber(rs.getString("policy_number"));
			policy.setPolicyState(rs.getInt("state"));
			policy.setModelId(rs.getInt("model_id"));
			policy.setCar(rs.getString("car"));
			policy.setInsurantName(rs.getString("insurant_name"));
			policy.setInsurantId(rs.getInt("insurant_id"));

			return policy;
		}
	}

}
