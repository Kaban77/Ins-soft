package ru.demidov.insSoft.policy;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PolicyCarRepository {

	private final JdbcTemplate jdbctemplate;

	private static final String SQL_INSERT = """
			insert into policies.t_policy_car (policy_id, model_id, year_of_issue, vin, register_sign, engine_power)
			values (:policyId, :modelId, :year, :vin, :sign, :power)
			""";
	private static final String SQL_UPDATE = """
			update policies.t_policy_car pc
			set model_id = :modelId,
			   pc.year_of_issue = :year,
			   pc.vin = :vin,
			   pc.register_sign = :sign,
			   pc.engine_power = :power
			   where pc.policy_id = :policyId
			""";

	public PolicyCarRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public void insert(Policy policy) {
		jdbctemplate.update(SQL_INSERT, policy.getPolicyId(), policy.getModelId(), policy.getYearOfIssueCar(), policy.getVin(),
				policy.getRegisterSign(), policy.getEnginePower());
	}

	public void update(Policy policy) {
		jdbctemplate.update(SQL_UPDATE, policy.getModelId(), policy.getYearOfIssueCar(), policy.getVin(), policy.getRegisterSign(),
				policy.getEnginePower(), policy.getPolicyId());
	}
}
