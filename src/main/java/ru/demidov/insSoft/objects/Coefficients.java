package ru.demidov.insSoft.objects;

public class Coefficients {
	private int policyId;
	private int tariff;
	private double bonus;
	private double power;
	private double season;
	private double ageAndExperience;
	private double period;
	private double driverLimit;
	private double territory;
	private double premium;

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium() {
		this.premium = tariff * bonus * power * season * ageAndExperience * period * driverLimit * territory;
	}

	public int getTariff() {
		return tariff;
	}

	public void setTariff(int tariff) {
		this.tariff = tariff;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double getSeason() {
		return season;
	}

	public void setSeason(double season) {
		this.season = season;
	}

	public double getAgeAndExperience() {
		return ageAndExperience;
	}

	public void setAgeAndExperience(double ageAndExperience) {
		this.ageAndExperience = ageAndExperience;
	}

	public double getPeriod() {
		return period;
	}

	public void setPeriod(double period) {
		this.period = period;
	}

	public double getDriverLimit() {
		return driverLimit;
	}

	public void setDriverLimit(double driverLimit) {
		this.driverLimit = driverLimit;
	}

	public double getTerritory() {
		return territory;
	}

	public void setTerritory(double territory) {
		this.territory = territory;
	}

}
