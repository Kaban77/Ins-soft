package ru.demidov.insSoft.policy.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.demidov.insSoft.policy.Policy;
import ru.demidov.insSoft.policy.PolicyForSearch;
import ru.demidov.insSoft.policy.PolicyManager;
import ru.demidov.insSoft.policy.PolicyStates;

@Controller
public class PolicyContoller {

	private final PolicyManager policyManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyContoller.class);

	public PolicyContoller(PolicyManager policyManager) {
		this.policyManager = policyManager;
	}

	@GetMapping(value = "/get-policies", produces = "application/json")
	@ResponseBody
	public List<Policy> getPolicies(@RequestParam(value = "dateFrom", required = false) String dateFrom, @RequestParam(value = "dateTo", required = false) String dateTo,
			@RequestParam(value = "policyNumber", required = false) String policyNumber) {
		try {
			var policy = new PolicyForSearch(policyNumber, dateFrom, dateTo);
			return policyManager.findPoliciesByParam(policy);
		} catch (Exception e) {
			LOGGER.error("Error!", e);
			throw new RuntimeException(e);
		}
	}

	@PostMapping(value = "/issue-policy", consumes = "application/json")
	public ResponseEntity<String> issuePolicy(@RequestBody Integer policyId) {
		try {
			policyManager.issuePolicy(policyId);

			return ResponseEntity.ok("OK");
		} catch (Exception e) {
			LOGGER.error("Error: policyId = " + policyId, e);
			throw new RuntimeException(e);
		}
	}

	@GetMapping(value = "/new-policy")
	public String newPolicy() {
		return "policy";
	}

	@GetMapping(value = "/open")
	public String openPolicy(@RequestParam(value = "policyId", required = true) Integer policyId, ModelMap model) {
		Policy policy = policyManager.findPoliciesById(policyId);
		model.addAttribute("policy", policy);

		String disabled = "";
		if (policy.getPolicyStateId() != PolicyStates.PROJECT.getState()) {
			disabled = "disabled";
		}
		model.addAttribute("disabled", disabled);

		return "policy";
	}

	@PostMapping(value = "/save-policy", produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Policy savePolicy(@RequestBody Policy policy) {
		try {
			if (policy.getPolicyId() == null) {
				return policyManager.insertPolicy(policy);
			} else {
				return policyManager.updatePolicy(policy);
			}
		} catch (Exception e) {
			LOGGER.error("Error!" + policy, e);
			throw new RuntimeException(e);
		}
	}

}
