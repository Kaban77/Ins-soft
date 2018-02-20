package ru.demidov.insSoft.interfaces;

import java.util.List;

import ru.demidov.insSoft.objects.Policy;

public interface PolicyDao {

	Policy findPolicy(String policyNumber);

	Policy findPolicy(int policyId);

	List<Policy> findPoliciesByParam(Policy policy);

}
