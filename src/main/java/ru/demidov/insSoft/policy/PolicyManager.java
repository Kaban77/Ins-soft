package ru.demidov.insSoft.policy;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.demidov.insSoft.coefficients.CoeffCalculator;

@Component
public class PolicyManager {

	private final CoeffCalculator coeffCalculator;
	private final PolicyRepository policyRepository;
	private final PolicyCarRepository policyCarRepository;
	private final PolicyCoefficientsRepository policyCoefficientsRepository;

	public PolicyManager(CoeffCalculator coeffCalculator, PolicyRepository policyRepository, PolicyCarRepository policyCarRepository,
			PolicyCoefficientsRepository policyCoefficientsRepository) {
		this.coeffCalculator = coeffCalculator;
		this.policyRepository = policyRepository;
		this.policyCarRepository = policyCarRepository;
		this.policyCoefficientsRepository = policyCoefficientsRepository;
	}

	@Transactional(readOnly = true)
	public Policy findPoliciesById(Integer policyId) {
		return policyRepository.select(policyId);
	}

	@Transactional(readOnly = true)
	public List<Policy> findPoliciesByParam(PolicyForSearch policy) {
		return policyRepository.select(policy);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Policy insertPolicy(Policy policy) {
		var policyId = policyRepository.getPolicyId();
		policy.setPolicyId(policyId);

		var coeff = coeffCalculator.calcPremium(policy);

		policyRepository.insert(policy, coeff.getPremium());
		policyCarRepository.insert(policy);
		policyCoefficientsRepository.insert(policyId, coeff);

		policy.setCoeff(coeff);
		return policy;
	}

	@Transactional
	public Policy updatePolicy(Policy policy) {
		var coeff = coeffCalculator.calcPremium(policy);
		policy.setCoeff(coeff);
		policyCoefficientsRepository.update(policy.getPolicyId(), coeff);
		policyCarRepository.update(policy);

		return policy;
	}

	@Transactional
	public void issuePolicy(Integer policyID) {
		var policy = findPoliciesById(policyID);
		policy.setPolicyStateId(PolicyStates.REGISTERED.getState());

		updatePolicy(policy);
	}
}
