package ru.demidov.insSoft.policy;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.demidov.insSoft.coefficients.Coefficients;
import ru.demidov.insSoft.documents.Document;
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

	public Policy() {

	}

	public Policy(ResultSet rs, int rowNum) throws SQLException {
		var document = new Document();

		document.setDateOfIssue(rs.getDate("date_of_issue_doc").toString());
		document.setId(rs.getInt("doc_id"));
		document.setNumber(rs.getString("doc_number"));
		document.setSerial(rs.getString("doc_serial"));

		var insurant = new Insurant();

		insurant.setDocument(document);
		insurant.setBirthDate(rs.getDate("birth_date").toString());
		insurant.setId(rs.getInt("insurant_id"));
		insurant.setName(rs.getString("name"));
		insurant.setPatronymic(rs.getString("patronymic"));
		insurant.setSurname(rs.getString("surname"));
		insurant.setSex(rs.getString("sex"));

		Coefficients coeff = new Coefficients();

		coeff.setAgeAndExperience(rs.getBigDecimal("age_and_experience"));
		coeff.setBonus(rs.getBigDecimal("bonus"));
		coeff.setDriverLimit(rs.getBigDecimal("driver_limit"));
		coeff.setPeriod(rs.getBigDecimal("period"));
		coeff.setPower(rs.getBigDecimal("power"));
		coeff.setSeason(rs.getBigDecimal("season"));
		coeff.setTariff(rs.getBigDecimal("tariff"));
		coeff.setTerritory(rs.getBigDecimal("territory"));
		coeff.setPremium();

		setInsurant(insurant);
		setCoeff(coeff);
		setPolicyId(rs.getInt("policy_id"));
		setPolicyNumber(rs.getString("policy_number"));
		setPolicyState(rs.getString("state_name"));
		setPolicyStateId(rs.getInt("state_id"));
		setBeginDate(rs.getDate("begin_date").toString());
		setEndDate(rs.getDate("end_date").toString());
		setInsutantFullName(rs.getString("insurant_full_name"));
		setBrandId(rs.getInt("brand_id"));
		setBrandName(rs.getString("brand_name"));
		setModelId(rs.getInt("model_id"));
		setModelName(rs.getString("model_name"));
		setEnginePower(rs.getInt("engine_power"));
		setRegisterSign(rs.getString("register_sign"));
		setVin(rs.getString("vin"));
		setYearOfIssueCar(rs.getInt("year_of_issue"));
	}

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
