package ru.demidov.insSoft.coefficients;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import ru.demidov.insSoft.coefficients.ageandexperience.AgeAndExperienceCoefficientRepository;
import ru.demidov.insSoft.coefficients.power.PowerCoefficientRepository;
import ru.demidov.insSoft.coefficients.tariff.TariffRepository;
import ru.demidov.insSoft.policy.Policy;

@Component
public class CoeffCalculator {

	private final TariffRepository tariffRepository;
	private final PowerCoefficientRepository powerCoefficientRepository;
	private final AgeAndExperienceCoefficientRepository ageAndExperienceCoefficientRepository;

	public CoeffCalculator(TariffRepository tariffRepository,
			PowerCoefficientRepository powerCoefficientRepository,
			AgeAndExperienceCoefficientRepository ageAndExperienceCoefficientRepository) {
		this.tariffRepository = tariffRepository;
		this.powerCoefficientRepository = powerCoefficientRepository;
		this.ageAndExperienceCoefficientRepository = ageAndExperienceCoefficientRepository;
	}

	public Coefficients calcPremium(Policy policy) {
		var coeff = new Coefficients();

		coeff.setAgeAndExperience(ageAndExperienceCoefficientRepository.getAgeAndExperienceCoefficient(policy.getInsurant().getId()));
		coeff.setBonus(calcBonus());
		coeff.setDriverLimit(calcDriverLimit());
		coeff.setPeriod(calcPeriod());
		coeff.setPower(powerCoefficientRepository.getPowerCoefficient(policy.getEnginePower()));
		coeff.setSeason(calcSeason());
		coeff.setTariff(tariffRepository.getTariff(policy.getModelId()));
		coeff.setTerritory(calcTerritory());
		coeff.setPremium();

		return coeff;
	}

	private BigDecimal calcBonus(/* int InsurantId */) {
		// TODO
		return BigDecimal.ONE;
	}

	private BigDecimal calcSeason(/* String beginDate, String endDate */) {
		// TODO
		return BigDecimal.ONE;
	}

	private BigDecimal calcPeriod(/* String beginDate, String endDate */) {
		// TODO
		return BigDecimal.ONE;
	}

	private BigDecimal calcDriverLimit() {
		// TODO
		return BigDecimal.ONE;
	}

	private BigDecimal calcTerritory(/* int insurantId */) {
		// TODO
		return BigDecimal.valueOf(2);
	}
}
