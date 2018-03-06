package ru.demidov.insSoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.demidov.insSoft.controllers.LoginController;
import ru.demidov.insSoft.interfaces.SelectDao;
import ru.demidov.insSoft.objects.Document;
import ru.demidov.insSoft.objects.Insurant;
import ru.demidov.insSoft.objects.Policy;
import ru.demidov.insSoft.objects.PolicyForSearch;

public class SelectDaoImpl implements SelectDao {

	private JdbcTemplate jdbctemplate;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	public SelectDaoImpl() {

	}

	public SelectDaoImpl(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	@Override
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
	public Policy findPolicy(int policyId) {

		String sql = "select * from policies.policy where policy_id = :policyId";
		try {
			return jdbctemplate.queryForObject(sql, new Object[] { policyId }, new PolicyRowMapper());
		} catch (IncorrectResultSizeDataAccessException dae) {
			return new Policy();
		}
	}

	@Override
	public Policy findPolicy(String policyNumber) {

		String sql = "select * from policies.policy where policy_number = :policy_number";
		try {
			return jdbctemplate.queryForObject(sql, new Object[] { policyNumber }, new PolicyRowMapper());
		} catch (IncorrectResultSizeDataAccessException dae) {
			return new Policy();
		}
	}

	@Override
	public List<Insurant> findInsurants(Insurant insurant) {

		String sql = "select * from policies.v_insurant_info where upper(name) like :name and upper(patronymic) like :patronymic and upper(surname) like :surname and doc_type = 3";
		ArrayList<String> args = new ArrayList<String>();

		args.add("%" + insurant.getName().toUpperCase().trim() + "%");
		args.add("%" + insurant.getPatronymic().toUpperCase().trim() + "%");
		args.add("%" + insurant.getSurname().toUpperCase().trim() + "%");

		if (tryParseDate(insurant.getBirthDate()) != false) {
			sql += " and birth_date = to_date(:birthDate, 'DD.MM.YYYY')";
			args.add(insurant.getBirthDate());
		}

		try {
			return jdbctemplate.query(sql, args.toArray(), new InsurantRowMapper());

		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ArrayList<Insurant>();
		}
	}

	@Override
	public List<Map<String, Object>> findBrands(String brandName) {
		String sql = "select * from policies.t_brand_directory where upper(brand_name) like :brandName";
		// return null;
		try {
			return jdbctemplate.query(sql, new Object[] { "%" + brandName.toUpperCase().trim() + "%" }, new ColumnMapRowMapper());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ArrayList<Map<String, Object>>();
		}
	}

	@Override
	public List<Map<String, Object>> findModels(Integer brandId) {
		String sql = "select model_id, model_name from policies.t_car_directory where brand_id = :brandId";
		try {
			return jdbctemplate.query(sql, new Object[] { brandId }, new ColumnMapRowMapper());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ArrayList<Map<String, Object>>();
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

	private static final class InsurantRowMapper implements RowMapper<Insurant> {

		@Override
		public Insurant mapRow(ResultSet rs, int rowNum) throws SQLException {
			Document document = new Document();
			document.setSerial(rs.getString("doc_serial"));
			document.setNumber(rs.getString("doc_number"));

			Insurant insurant = new Insurant();

			insurant.setDocument(document);
			insurant.setId(rs.getInt("insurant_id"));
			insurant.setName(rs.getString("name"));
			insurant.setPatronymic(rs.getString("patronymic"));
			insurant.setSurname(rs.getString("surname"));
			insurant.setBirthDate(rs.getDate("birth_date").toString());
			insurant.setSex(rs.getString("sex"));
			return insurant;
		}
	}

}
