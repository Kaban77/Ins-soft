package ru.demidov.insSoft.cars.brands;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandOfCar {

	private int id;
	private String name;

	public BrandOfCar() {
	}

	public BrandOfCar(ResultSet rs, int rowNum) throws SQLException {
		this.id = rs.getInt("brand_id");
		this.name = rs.getString("brand_name");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BrandOfCar [id=" + id + ", name=" + name + "]";
	}

}
