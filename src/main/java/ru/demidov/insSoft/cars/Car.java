package ru.demidov.insSoft.cars;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Car {

	private int modelId;
	private String modelName;
	private int brandId;

	public Car() {
	}

	public Car(ResultSet rs, int rowNum) throws SQLException {
		this.modelId = rs.getInt("model_id");
		this.modelName = rs.getString("model_name");
		this.brandId = rs.getInt("brand_id");
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	@Override
	public String toString() {
		return "Car [modelId=" + modelId + ", modelName=" + modelName + ", brandId=" + brandId + "]";
	}

}
