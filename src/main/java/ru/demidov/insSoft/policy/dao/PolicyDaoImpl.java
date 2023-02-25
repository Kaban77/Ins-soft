package ru.demidov.insSoft.policy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.demidov.insSoft.db.CoeffCalculator;
import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.Document;
import ru.demidov.insSoft.objects.Insurant;
import ru.demidov.insSoft.policy.Policy;
import ru.demidov.insSoft.policy.PolicyForSearch;
import ru.demidov.insSoft.policy.PolicyStates;


@Component
public class PolicyDaoImpl {

	@Autowired
	private JdbcTemplate jdbctemplate;

	private static final Logger logger = LoggerFactory.getLogger(PolicyDaoImpl.class);

	private static final String getPolicyById = "select * from policies.v_policy_full where policy_id = :policyId";
	private static final String getPolicyId = "select policies.pkg_utilities.get_id('t_policy') from dual";
	private static final String insertPolicy = "insert into policies.t_policy (policy_id, policy_number, begin_date, end_date, insurant_id, state, premium) values (:policyId, :policyNo, sysdate, trunc(sysdate) + 364, :insurantId, 0, :premium)";
	private static final String insertCar = "insert into policies.t_policy_car (policy_id, model_id, year_of_issue, vin, register_sign, engine_power) values (:policyId, :modelId, :year, :vin, :sign, :power)";
	private static final String insertCoeff = "insert into policies.t_policy_coefficients (policy_id, tariff, bonus, power, season, age_and_experience, period, driver_limit, territory) values(:policyId, :tariff, :bonus, :power, :season,:ageExperience, :period,:driver_limit, :territory)";
	private static final String updateCar = "update policies.t_policy_car pc set model_id = :modelId, pc.year_of_issue = :year, pc.vin = :vin, pc.register_sign = :sign, pc.engine_power = :power where pc.policy_id = :policyId";
	private static final String updateCoeff = "update policies.t_policy_coefficients set tariff = :tariff, bonus = :bonus, power = :power, season = :season, age_and_experience = :ex, period = :period, driver_limit = :limit, territory = :territory where policy_id = :policyId";
	private static final String updatePremium = "update policies.t_policy set premium = :premium where policy_id = :policyId";
	private static final String updateState = "update policies.t_policy set state = :state where policy_id = :policyId";

	@Transactional(readOnly = true)
	public Policy findPoliciesById(Integer policyId) {
		try {
			return jdbctemplate.queryForObject(getPolicyById, new Object[] { policyId }, new PolicyRowMapper());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new Policy();
		}
	}

	@Transactional(readOnly = true)
	public List<Policy> findPoliciesByParam(PolicyForSearch policy) {

		String sql = "select * from policies.v_policy_full where upper(policy_number) like :policyNumber";
		ArrayList<String> args = new ArrayList<String>();

		args.add("%" + policy.getPolicyNumber().toUpperCase().trim() + "%");

		if (tryParseDate(policy.getStartPeriod()) != false) {
			sql += " and begin_date >= to_date(:beginDate, 'DD.MM.YYYY')";
			args.add(policy.getStartPeriod());
		}
		if (tryParseDate(policy.getEndPeriod()) != false) {
			sql += " and begin_date <= to_date(:endDate, 'DD.MM.YYYY')";
			args.add(policy.getEndPeriod());
		}

		try {
			return jdbctemplate.query(sql, args.toArray(), new PolicyRowMapper());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ArrayList<Policy>();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Policy insertPolicy(Policy policy) {
		try {
			int policyId = jdbctemplate.queryForObject(getPolicyId, Integer.class);
			policy.setPolicyId(policyId);

			Coefficients coeff = new CoeffCalculator(jdbctemplate).calcPremium(policy);

			jdbctemplate.update(insertPolicy, new Object[] { policyId, "EEE" + policyId, policy.getInsurant().getId(), coeff.getPremium() });
			jdbctemplate.update(insertCar, new Object[] { policyId, policy.getModelId(), policy.getYearOfIssueCar(), policy.getVin(), policy.getRegisterSign(), policy.getEnginePower() });
			jdbctemplate.update(insertCoeff, new Object[] { policyId, coeff.getTariff(), coeff.getBonus(), coeff.getPower(), coeff.getSeason(), coeff.getAgeAndExperience(), coeff.getPeriod(),
					coeff.getDriverLimit(), coeff.getTerritory() });

			policy.setCoeff(coeff);
			return policy;

		} catch (Exception e) {
			logger.info(" insertPolicy " + e.getMessage());
			return new Policy();
		}
	}

	@Transactional
	public Policy updatePolicy(Policy policy) {
		try {
			if (policy.getInsurant().getId() != 0) {
				String updateInsurant = "update policies.t_policy set insurant_id = :insurantId where policy_id = :policyId";
				jdbctemplate.update(updateInsurant, new Object[] { policy.getInsurant().getId(), policy.getPolicyId() });
			} else {
				String selectInsurantId = "select insurant_id from policies.t_policy where policy_id = :policyId";
				int insurantId = jdbctemplate.queryForObject(selectInsurantId, Integer.class, new Object[] { policy.getPolicyId() });

				Insurant insurant = new Insurant();
				insurant.setId(insurantId);
				policy.setInsurant(insurant);
			}
			jdbctemplate.update(updateCar, new Object[] { policy.getModelId(), policy.getYearOfIssueCar(), policy.getVin(),
					policy.getRegisterSign(), policy.getEnginePower(), policy.getPolicyId() });

			Coefficients coeff = new CoeffCalculator(jdbctemplate).calcPremium(policy);

			jdbctemplate.update(updateCoeff, new Object[] { coeff.getTariff(), coeff.getBonus(), coeff.getPower(), coeff.getSeason(), coeff.getAgeAndExperience(), coeff.getPeriod(),
					coeff.getDriverLimit(), coeff.getTerritory(), policy.getPolicyId() });
			jdbctemplate.update(updatePremium, new Object[] { coeff.getPremium(), policy.getPolicyId() });

			return policy;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new Policy();
		}
	}

	@Transactional
	public boolean issuePolicy(int policyID) {
		return changeState(policyID, PolicyStates.REGISTERED);
	}

	private boolean changeState(int policyId, PolicyStates state) {
		try {
			jdbctemplate.update(updateState, new Object[] { state.getState(), policyId });
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		}
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

	private static final class PolicyRowMapper implements RowMapper<Policy> {

		@Override
		public Policy mapRow(ResultSet rs, int rowNum) throws SQLException {
			Document document = rowDocument(rs);
			Insurant insurant = rowInsurant(rs, document);
			Coefficients coeff = rowCoefficients(rs);

			Policy policy = new Policy();

			policy.setInsurant(insurant);
			policy.setCoeff(coeff);
			policy.setPolicyId(rs.getInt("policy_id"));
			policy.setPolicyNumber(rs.getString("policy_number"));
			policy.setPolicyState(rs.getString("state_name"));
			policy.setPolicyStateId(rs.getInt("state_id"));
			policy.setBeginDate(rs.getDate("begin_date").toString());
			policy.setEndDate(rs.getDate("end_date").toString());
			policy.setInsutantFullName(rs.getString("insurant_full_name"));
			policy.setBrandId(rs.getInt("brand_id"));
			policy.setBrandName(rs.getString("brand_name"));
			policy.setModelId(rs.getInt("model_id"));
			policy.setModelName(rs.getString("model_name"));
			policy.setEnginePower(rs.getInt("engine_power"));
			policy.setRegisterSign(rs.getString("register_sign"));
			policy.setVin(rs.getString("vin"));
			policy.setYearOfIssueCar(rs.getInt("year_of_issue"));

			return policy;
		}

		private Document rowDocument(ResultSet rs) throws SQLException {
			Document document = new Document();

			document.setDateOfIssue(rs.getDate("date_of_issue_doc").toString());
			document.setId(rs.getInt("doc_id"));
			document.setNumber(rs.getString("doc_number"));
			document.setSerial(rs.getString("doc_serial"));

			return document;
		}

		private Insurant rowInsurant(ResultSet rs, Document document) throws SQLException {
			Insurant insurant = new Insurant();

			insurant.setDocument(document);
			insurant.setBirthDate(rs.getDate("birth_date").toString());
			insurant.setId(rs.getInt("insurant_id"));
			insurant.setName(rs.getString("name"));
			insurant.setPatronymic(rs.getString("patronymic"));
			insurant.setSurname(rs.getString("surname"));
			insurant.setSex(rs.getString("sex"));

			return insurant;
		}

		private Coefficients rowCoefficients(ResultSet rs) throws SQLException {
			Coefficients coeff = new Coefficients();

			coeff.setAgeAndExperience(rs.getDouble("age_and_experience"));
			coeff.setBonus(rs.getDouble("bonus"));
			coeff.setDriverLimit(rs.getDouble("driver_limit"));
			coeff.setPeriod(rs.getDouble("period"));
			coeff.setPower(rs.getDouble("power"));
			coeff.setSeason(rs.getDouble("season"));
			coeff.setTariff(rs.getInt("tariff"));
			coeff.setTerritory(rs.getDouble("territory"));
			coeff.setPremium();

			return coeff;
		}
	}

}
