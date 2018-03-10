package ru.demidov.insSoft.interfaces;

import java.util.List;

import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.Policy;
import ru.demidov.insSoft.objects.PolicyForSearch;
import ru.demidov.insSoft.objects.PolicyFromDB;
import ru.demidov.insSoft.objects.PolicyToDB;

public interface PolicyDao {

	List<Policy> findPoliciesByParam(PolicyForSearch policy);

	Coefficients insertPolicy(PolicyToDB policy);

	Coefficients updatePolicy(PolicyToDB policy);

	boolean issuePolicy(int policyID);

	PolicyFromDB findPoliciesById(Integer policyId);

}
