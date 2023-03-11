package ru.demidov.insSoft.coefficients.power;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PowerCoefficientRepository {

	private final JdbcTemplate jdbctemplate;

	private static final String SQL_GET_POWER_COEFF = """
			select coefficient
			from policies.t_power_coeff
			where :power between min_power and max_power
			    """;

	public PowerCoefficientRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public BigDecimal getPowerCoefficient(int power) {
		return jdbctemplate.queryForObject(SQL_GET_POWER_COEFF, BigDecimal.class, new Object[] { power });
	}
}
