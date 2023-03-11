package ru.demidov.insSoft.coefficients.ageandexperience;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AgeAndExperienceCoefficientRepository {

	private final JdbcTemplate jdbctemplate;
	private static final String SQL_GET_COEFF = """
			select e.coefficient
			from policies.t_scope_of_experience e,
			(
			       select trunc(months_between(sysdate, i.birth_date) / 12) age, trunc(months_between(sysdate, id.date_of_issue) / 12) ex
			       from policies.t_insurants i
			       join policies.t_insurant_docs id on i.insurant_id = id.insurant_id
					where i.insurant_id = :insurantId and id.doc_type = 3
			)tt
			where tt.age between e.min_age and e.max_age
			and tt.ex between e.min_experience and e.max_experience
			    """;

	public AgeAndExperienceCoefficientRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public BigDecimal getAgeAndExperienceCoefficient(int insurantId) {
		return jdbctemplate.queryForObject(SQL_GET_COEFF, BigDecimal.class, new Object[] { insurantId });
	}
}
