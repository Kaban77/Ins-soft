package ru.demidov.insSoft.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.PolicyToDB;

public class CoeffCalculator {

	private JdbcTemplate jdbctemplate;

	private static final Logger logger = LoggerFactory.getLogger(CoeffCalculator.class);

	public CoeffCalculator() {

	}

	public CoeffCalculator(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public Coefficients calcPremium(Policy policy) {
		Coefficients coeff = new Coefficients();

		coeff.setAgeAndExperience(calcAgeAndExperience(policy.getInsurant().getId()));
		coeff.setBonus(calcBonus());
		coeff.setDriverLimit(calcDriverLimit());
		coeff.setPeriod(calcPeriod());
		coeff.setPower(calcPower(policy.getEnginePower()));
		coeff.setSeason(calcSeason());
		coeff.setTariff(calcTariff(policy.getModelId()));
		coeff.setTerritory(calcTerritory());
		coeff.setPremium();

		return coeff;
	}

	private int calcTariff(Integer modelId) {
		String sql = "select t.tariff " +
			     "from policies.t_tariff t " +
			     "where t.car_category = " +
			     "( " +
			         "select cd.category " +
			         "from policies.t_car_directory cd " +
			         "where cd.model_id = :modelId " +
			     ")";
		try {
			return jdbctemplate.queryForObject(sql, Integer.class, new Object[] { modelId });
		} catch (Exception e) {
			logger.info(" calcTariff " + e.getMessage());
			return 0;
		}
	}

	private double calcBonus(/* int InsurantId */) {
		// TODO
		return 1.0;
	}

	private double calcPower(int power) {
		String sql = "select coefficient " +
			     "from policies.t_power_coeff " +
			     "where :power between min_power and max_power";

		try {
			return jdbctemplate.queryForObject(sql, Double.class, new Object[] { power });
		} catch (Exception e) {
			logger.info(" calcPower " + e.getMessage());
			return 0.0;
		}
	}

	private double calcSeason(/* String beginDate, String endDate */) {
		// TODO
		return 1.0;
	}

	private double calcAgeAndExperience(int insurantId) {
		String sql = "select e.coefficient " +
			     "from policies.t_scope_of_experience e, " +
			     "( " +
			         "select trunc(months_between(sysdate, i.birth_date) / 12) age, trunc(months_between(sysdate, id.date_of_issue) / 12) ex " + 
			         "from policies.t_insurants i " + 
			         "join policies.t_insurant_docs id on i.insurant_id = id.insurant_id " +
				 "where i.insurant_id = :insurantId and id.doc_type = 3 " +
			     ")tt " + 
			     "where tt.age between e.min_age and e.max_age " +
			     "and tt.ex between e.min_experience and e.max_experience";

		try {
			return jdbctemplate.queryForObject(sql, Double.class, new Object[] { insurantId });
		} catch (Exception e) {
			logger.info(" calcAgeAndExperience " + e.getMessage());
			return 0.0;
		}
	}

	private double calcPeriod(/* String beginDate, String endDate */) {
		// TODO
		return 1.0;
	}

	private double calcDriverLimit() {
		// TODO
		return 1.0;
	}

	private double calcTerritory(/* int insurantId */) {
		// TODO
		return 2.0;
	}
}
