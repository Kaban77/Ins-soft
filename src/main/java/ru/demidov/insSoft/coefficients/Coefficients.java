package ru.demidov.insSoft.coefficients;

import java.math.BigDecimal;
import java.util.Objects;

public class Coefficients {
	private int policyId;
	private BigDecimal tariff;
	private BigDecimal bonus;
	private BigDecimal power;
	private BigDecimal season;
	private BigDecimal ageAndExperience;
	private BigDecimal period;
	private BigDecimal driverLimit;
	private BigDecimal territory;
	private BigDecimal premium;

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium() {
		this.premium = tariff.multiply(bonus).multiply(power).multiply(season).multiply(ageAndExperience).multiply(period)
				.multiply(driverLimit).multiply(territory);
	}

	public BigDecimal getTariff() {
		return tariff;
	}

	public void setTariff(BigDecimal tariff) {
		this.tariff = tariff;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	public BigDecimal getPower() {
		return power;
	}

	public void setPower(BigDecimal power) {
		this.power = power;
	}

	public BigDecimal getSeason() {
		return season;
	}

	public void setSeason(BigDecimal season) {
		this.season = season;
	}

	public BigDecimal getAgeAndExperience() {
		return ageAndExperience;
	}

	public void setAgeAndExperience(BigDecimal ageAndExperience) {
		this.ageAndExperience = ageAndExperience;
	}

	public BigDecimal getPeriod() {
		return period;
	}

	public void setPeriod(BigDecimal period) {
		this.period = period;
	}

	public BigDecimal getDriverLimit() {
		return driverLimit;
	}

	public void setDriverLimit(BigDecimal driverLimit) {
		this.driverLimit = driverLimit;
	}

	public BigDecimal getTerritory() {
		return territory;
	}

	public void setTerritory(BigDecimal territory) {
		this.territory = territory;
	}

	@Override
	public String toString() {
		return "Coefficients [policyId=" + policyId + ", tariff=" + tariff + ", bonus=" + bonus + ", power=" + power + ", season=" + season
				+ ", ageAndExperience=" + ageAndExperience + ", period=" + period + ", driverLimit=" + driverLimit + ", territory="
				+ territory + ", premium=" + premium + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ageAndExperience, bonus, driverLimit, period, policyId, power, premium, season, tariff, territory);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Coefficients other = (Coefficients) obj;
		return Objects.equals(ageAndExperience, other.ageAndExperience) && Objects.equals(bonus, other.bonus)
				&& Objects.equals(driverLimit, other.driverLimit) && Objects.equals(period, other.period) && policyId == other.policyId
				&& Objects.equals(power, other.power) && Objects.equals(premium, other.premium) && Objects.equals(season, other.season)
				&& tariff == other.tariff && Objects.equals(territory, other.territory);
	}
}
