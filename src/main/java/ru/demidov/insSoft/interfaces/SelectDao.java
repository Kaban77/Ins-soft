package ru.demidov.insSoft.interfaces;

import java.util.List;
import java.util.Map;

import ru.demidov.insSoft.objects.Insurant;
import ru.demidov.insSoft.objects.Policy;
import ru.demidov.insSoft.objects.PolicyForSearch;

public interface SelectDao {

	Policy findPolicy(String policyNumber);

	Policy findPolicy(int policyId);

	List<Policy> findPoliciesByParam(PolicyForSearch policy);

	List<Insurant> findInsurants(Insurant insurant);

	List<Map<String, Object>> findBrands(String brandName);

	List<Map<String, Object>> findModels(Integer brandId);

}
