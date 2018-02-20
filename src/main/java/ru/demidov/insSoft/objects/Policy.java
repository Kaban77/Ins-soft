package ru.demidov.insSoft.objects;

public class Policy {
	private String policyNumber;
	private int policyId;
	private String beginDate;
	private String endDate;
	private String car;
	private int modelId;
	private String insurantName;
	private int insurantId;
	private int policyState;

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public int getInsurantId() {
		return insurantId;
	}

	public void setInsurantId(int insurantId) {
		this.insurantId = insurantId;
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

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getInsurantName() {
		return insurantName;
	}

	public void setInsurantName(String insurantName) {
		this.insurantName = insurantName;
	}

	public int getPolicyState() {
		return policyState;
	}

	public void setPolicyState(int policyState) {
		this.policyState = policyState;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

}
