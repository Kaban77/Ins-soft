package ru.demidov.insSoft.cars;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CarRepository {

	private final JdbcTemplate jdbctemplate;
	private static final String SQL_GET_BY_BRAND = "select * from policies.t_car_directory where brand_id = :brandId";

	public CarRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public List<Car> getByBrand(Integer brandId) {
		return jdbctemplate.query(SQL_GET_BY_BRAND, Car::new, brandId);
	}
}
