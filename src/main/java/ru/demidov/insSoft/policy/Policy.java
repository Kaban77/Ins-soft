package ru.demidov.insSoft.policy;

import ru.demidov.insSoft.coefficients.Coefficients;
import ru.demidov.insSoft.insurant.Insurant;

public class Policy {
	private Integer policyId;
	private String policyNumber;
	private String beginDate;
	private String endDate;
	private String policyState;
	private int policyStateId;
	private Insurant insurant;
	private String insutantFullName;
	private Integer brandId;
	private String brandName;
	private Integer modelId;
	private String modelName;
	private int yearOfIssueCar;
	private String vin;
	private String registerSign;
	private int enginePower;
	private Coefficients coeff;

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPolicyState() {
		return policyState;
	}

	public void setPolicyState(String policyState) {
		this.policyState = policyState;
	}

	public int getPolicyStateId() {
		return policyStateId;
	}

	public void setPolicyStateId(int policyStateId) {
		this.policyStateId = policyStateId;
	}

	public Insurant getInsurant() {
		return insurant;
	}

	public void setInsurant(Insurant insurant) {
		this.insurant = insurant;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getYearOfIssueCar() {
		return yearOfIssueCar;
	}

	public void setYearOfIssueCar(int yearOfIssueCar) {
		this.yearOfIssueCar = yearOfIssueCar;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getRegisterSign() {
		return registerSign;
	}

	public void setRegisterSign(String registerSign) {
		this.registerSign = registerSign;
	}

	public int getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(int enginePower) {
		this.enginePower = enginePower;
	}

	public Coefficients getCoeff() {
		return coeff;
	}

	public void setCoeff(Coefficients coeff) {
		this.coeff = coeff;
	}

	public String getInsutantFullName() {
		return insutantFullName;
	}

	public void setInsutantFullName(String insutantFullName) {
		this.insutantFullName = insutantFullName;
	}

	@Override
	public String toString() {
		return "Policy [policyId=" + policyId + ", policyNumber=" + policyNumber + ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", policyState=" + policyState + ", policyStateId=" + policyStateId + ", insurant=" + insurant + ", insutantFullName="
				+ insutantFullName + ", brandId=" + brandId + ", brandName=" + brandName + ", modelId=" + modelId + ", modelName="
				+ modelName + ", yearOfIssueCar=" + yearOfIssueCar + ", vin=" + vin + ", registerSign=" + registerSign + ", enginePower="
				+ enginePower + ", coeff=" + coeff + "]";
	}

}
