package ru.demidov.insSoft.objects;

public class PolicyFromDB {

	private Integer policyId;
	private int policyState;
	private Integer insurantId;
	private String insutantFullName;
	private String licenseSerial;
	private String licenseNumber;
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

	public int getPolicyState() {
		return policyState;
	}

	public void setPolicyState(int policyState) {
		this.policyState = policyState;
	}

	public Integer getInsurantId() {
		return insurantId;
	}

	public void setInsurantId(Integer insurantId) {
		this.insurantId = insurantId;
	}

	public String getInsutantFullName() {
		return insutantFullName;
	}

	public void setInsutantFullName(String insutantFullName) {
		this.insutantFullName = insutantFullName;
	}

	public String getLicenseSerial() {
		return licenseSerial;
	}

	public void setLicenseSerial(String licenseSerial) {
		this.licenseSerial = licenseSerial;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
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

}
