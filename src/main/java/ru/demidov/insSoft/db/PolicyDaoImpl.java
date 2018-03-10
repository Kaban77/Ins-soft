package ru.demidov.insSoft.db;

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

import ru.demidov.insSoft.interfaces.PolicyDao;
import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.Policy;
import ru.demidov.insSoft.objects.PolicyForSearch;
import ru.demidov.insSoft.objects.PolicyFromDB;
import ru.demidov.insSoft.objects.PolicyStates;
import ru.demidov.insSoft.objects.PolicyToDB;

@Component
public class PolicyDaoImpl implements PolicyDao {

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

	@Override
	@Transactional(readOnly = true)
	public PolicyFromDB findPoliciesById(Integer policyId) {
		try {
			return jdbctemplate.queryForObject(getPolicyById, new Object[] { policyId }, new PolicyFromDBRowMapper());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new PolicyFromDB();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Policy> findPoliciesByParam(PolicyForSearch policy) {

		String sql = "select * from policies.v_policy_info where upper(policy_number) like :policyNumber";
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Coefficients insertPolicy(PolicyToDB policy) {
		try {
			int policyId = jdbctemplate.queryForObject(getPolicyId, Integer.class);
			policy.setPolicyId(policyId);

			Coefficients coeff = new CoeffCalculator(jdbctemplate).calcPremium(policy);

			jdbctemplate.update(insertPolicy, new Object[] { policyId, "EEE" + policyId, policy.getInsurantId(), coeff.getPremium() });
			jdbctemplate.update(insertCar, new Object[] { policyId, policy.getModelId(), policy.getYearOfIssue(), policy.getVin(), policy.getRegisterSign(), policy.getEnginePower() });
			jdbctemplate.update(insertCoeff, new Object[] { policyId, coeff.getTariff(), coeff.getBonus(), coeff.getPower(), coeff.getSeason(), coeff.getAgeAndExperience(), coeff.getPeriod(),
					coeff.getDriverLimit(), coeff.getTerritory() });

			return coeff;

		} catch (Exception e) {
			logger.info(" insertPolicy " + e.getMessage());
			return new Coefficients();
		}
	}

	@Override
	@Transactional
	public Coefficients updatePolicy(PolicyToDB policy) {
		try {
			if (policy.getInsurantId() != null) {
				String updateInsurant = "update policies.t_policy set insurant_id = :insurantId where policy_id = :policyId";
				jdbctemplate.update(updateInsurant, new Object[] { policy.getInsurantId(), policy.getPolicyId() });
			} else {
				String selectInsurantId = "select insurant_id from policies.t_policy where policy_id = :policyId";
				int insurantId = jdbctemplate.queryForObject(selectInsurantId, Integer.class, new Object[] { policy.getPolicyId() });

				policy.setInsurantId(insurantId);
			}
			jdbctemplate.update(updateCar, new Object[] { policy.getModelId(), policy.getYearOfIssue(), policy.getVin(), policy.getRegisterSign(), policy.getEnginePower(), policy.getPolicyId() });

			Coefficients coeff = new CoeffCalculator(jdbctemplate).calcPremium(policy);

			jdbctemplate.update(updateCoeff, new Object[] { coeff.getTariff(), coeff.getBonus(), coeff.getPower(), coeff.getSeason(), coeff.getAgeAndExperience(), coeff.getPeriod(),
					coeff.getDriverLimit(), coeff.getTerritory(), coeff.getPolicyId() });
			jdbctemplate.update(updatePremium, new Object[] { coeff.getPremium(), coeff.getPolicyId() });

			return coeff;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new Coefficients();
		}
	}

	@Override
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

	private static final class PolicyFromDBRowMapper implements RowMapper<PolicyFromDB> {

		@Override
		public PolicyFromDB mapRow(ResultSet rs, int rowNum) throws SQLException {
			PolicyFromDB policy = new PolicyFromDB();
			Coefficients coeff = new Coefficients();

			coeff.setAgeAndExperience(rs.getDouble("age_and_experience"));
			coeff.setBonus(rs.getDouble("bonus"));
			coeff.setDriverLimit(rs.getDouble("driver_limit"));
			coeff.setPeriod(rs.getDouble("period"));
			coeff.setPolicyId(rs.getInt("policy_id"));
			coeff.setPower(rs.getDouble("power"));
			coeff.setSeason(rs.getDouble("season"));
			coeff.setTariff(rs.getInt("tariff"));
			coeff.setTerritory(rs.getDouble("territory"));
			coeff.setPremium();

			policy.setCoeff(coeff);
			policy.setPolicyId(coeff.getPolicyId());
			policy.setBrandId(rs.getInt("brand_id"));
			policy.setBrandName(rs.getString("brand_name"));
			policy.setEnginePower(rs.getInt("engine_power"));
			policy.setInsurantId(rs.getInt("insurant_id"));
			policy.setInsutantFullName(rs.getString("full_name"));
			policy.setLicenseNumber(rs.getString("doc_number"));
			policy.setLicenseSerial(rs.getString("doc_serial"));
			policy.setModelId(rs.getInt("model_id"));
			policy.setModelName(rs.getString("model_name"));
			policy.setPolicyState(rs.getInt("state"));
			policy.setRegisterSign(rs.getString("register_sign"));
			policy.setVin(rs.getString("vin"));
			policy.setYearOfIssueCar(rs.getInt("year_of_issue"));

			return policy;
		}
	}

}
