package ru.demidov.insSoft.cars;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CarDao {

	@Autowired
	private JdbcTemplate jdbctemplate;

	private static final String getBrands = "select brand_id, brand_name from policies.t_brand_directory where upper(brand_name) like :brandName";
	private static final String getModels = "select model_id, model_name from policies.t_car_directory where brand_id = :brandId";

	private static final Logger logger = LoggerFactory.getLogger(CarDao.class);

	public List<Map<String, Object>> findBrands(String brandName) {
		try {
			return jdbctemplate.query(getBrands, new Object[] { "%" + brandName.toUpperCase().trim() + "%" }, new ColumnMapRowMapper());
		} catch (Exception e) {
			logger.info(" findBrands " + e.getMessage());
			return new ArrayList<Map<String, Object>>();
		}
	}

	public List<Map<String, Object>> findModels(Integer brandId) {
		try {
			return jdbctemplate.query(getModels, new Object[] { brandId }, new ColumnMapRowMapper());
		} catch (Exception e) {
			logger.info(" findModels " + e.getMessage());
			return new ArrayList<Map<String, Object>>();
		}
	}
}
