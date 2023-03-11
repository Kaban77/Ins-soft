package ru.demidov.insSoft.coefficients.tariff;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TariffRepository {
	
	private static final String SQL_GET_TARIFF = """
				select t.tariff
			    from policies.t_tariff t
			    where t.car_category =
			    (
			         select cd.category
			         from policies.t_car_directory cd
			         where cd.model_id = :modelId
			    )
			""";

	private final JdbcTemplate jdbctemplate;

	public TariffRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public BigDecimal getTariff(Integer modelId) {
		return jdbctemplate.queryForObject(SQL_GET_TARIFF, BigDecimal.class, new Object[] { modelId });
	}
}
