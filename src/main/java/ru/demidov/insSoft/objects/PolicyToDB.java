package ru.demidov.insSoft.objects;

public class PolicyToDB {

	private Integer policyId;
	private Integer insurantId;
	private Integer brandId;
	private String modelName;
	private int yearOfIssue;
	private String vin;
	private String registerSign;
	private int enginePower;

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public Integer getInsurantId() {
		return insurantId;
	}

	public void setInsurantId(Integer insurantId) {
		this.insurantId = insurantId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getYearOfIssue() {
		return yearOfIssue;
	}

	public void setYearOfIssue(int yearOfIssue) {
		this.yearOfIssue = yearOfIssue;
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

}
