package ru.demidov.insSoft.policy;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.demidov.insSoft.coefficients.Coefficients;

@Component
public class PolicyCoefficientsRepository {

	private final JdbcTemplate jdbctemplate;

	private static final String SQL_INSERT = "insert into policies.t_policy_coefficients (policy_id, tariff, bonus, power, season, age_and_experience, period, driver_limit, territory) values(:policyId, :tariff, :bonus, :power, :season,:ageExperience, :period,:driver_limit, :territory)";
	private static final String SQL_UPDATE = """
			update policies.t_policy_coefficients
			set tariff = :tariff,
			bonus = :bonus,
			power = :power,
			season = :season,
			age_and_experience = :ex,
			period = :period,
			driver_limit = :limit,
			territory = :territory
			where policy_id = :policyId
			""";

	public PolicyCoefficientsRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public void insert(Integer policyId, Coefficients coeff) {
		jdbctemplate.update(SQL_INSERT, policyId, coeff.getTariff(), coeff.getBonus(), coeff.getPower(), coeff.getSeason(),
				coeff.getAgeAndExperience(), coeff.getPeriod(), coeff.getDriverLimit(), coeff.getTerritory());
	}

	public void update(Integer policyId, Coefficients coeff) {
		jdbctemplate.update(SQL_UPDATE, coeff.getTariff(), coeff.getBonus(), coeff.getPower(), coeff.getSeason(),
				coeff.getAgeAndExperience(), coeff.getPeriod(), coeff.getDriverLimit(), coeff.getTerritory(), policyId);
	}
}
