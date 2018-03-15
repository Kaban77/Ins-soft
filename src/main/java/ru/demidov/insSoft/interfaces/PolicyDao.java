package ru.demidov.insSoft.interfaces;

import java.util.List;

import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.Policy;
import ru.demidov.insSoft.objects.PolicyForSearch;

public interface PolicyDao {

	List<Policy> findPoliciesByParam(PolicyForSearch policy);

	Policy insertPolicy(Policy policy);

	Policy updatePolicy(Policy policy);

	boolean issuePolicy(int policyID);

	Policy findPoliciesById(Integer policyId);

}
