package ru.demidov.insSoft.cars.brands;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BrandRepository {

	private static final String SQL_SEARCH = "select brand_id, brand_name from policies.t_brand_directory where upper(brand_name) like :brandName";

	private final JdbcTemplate jdbctemplate;

	public BrandRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public List<BrandOfCar> search(String brandName) {
		return jdbctemplate.query(SQL_SEARCH, BrandOfCar::new, brandName);
	}
}
