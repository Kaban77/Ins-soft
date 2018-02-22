package ru.demidov.insSoft.objects;

public class PolicyForSearch {

	private String policyNumber;
	private String startPeriod;
	private String endPeriod;

	public PolicyForSearch() {
	}

	public PolicyForSearch(String policyNumber, String startPeriod, String endPeriod) {
		this.policyNumber = policyNumber;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}

}
