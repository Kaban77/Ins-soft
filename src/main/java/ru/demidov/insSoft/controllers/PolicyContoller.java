package ru.demidov.insSoft.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.demidov.insSoft.db.PolicyDaoImpl;
import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.Policy;
import ru.demidov.insSoft.objects.PolicyForSearch;
import ru.demidov.insSoft.objects.PolicyFromDB;
import ru.demidov.insSoft.objects.PolicyStates;
import ru.demidov.insSoft.objects.PolicyToDB;

@Controller
public class PolicyContoller {

	@Autowired
	private PolicyDaoImpl policyManager;

	// private static final Logger logger =
	// LoggerFactory.getLogger(PolicyContoller.class);

	@RequestMapping(value = "/get-policies", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Policy> getPolicies(@RequestParam(value = "dateFrom", required = false) String dateFrom, @RequestParam(value = "dateTo", required = false) String dateTo,
			@RequestParam(value = "policyNumber", required = false) String policyNumber) {

		PolicyForSearch policy = new PolicyForSearch(policyNumber, dateFrom, dateTo);

		return policyManager.findPoliciesByParam(policy);
	}

	@RequestMapping(value = "/issue-policy", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> issuePolicy(@RequestBody Integer policyId) {
		if (policyManager.issuePolicy(policyId))
			return new ResponseEntity<String>(HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error in issue-policy. Check log for more information", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/new-policy", method = RequestMethod.GET)
	public String newPolicy() {
		return "policy";
	}

	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public String openPolicy(@RequestParam(value = "policyId", required = true) Integer policyId, ModelMap model) {
		Policy policy = policyManager.findPoliciesById(policyId);
		model.addAttribute("policy", policy);

		String disabled = "";
		if (policy.getPolicyState() != PolicyStates.PROJECT.getState())
			disabled = "disabled";
		model.addAttribute("disabled", disabled);

		return "policy";
	}

	@RequestMapping(value = "/save-policy", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Policy savePolicy(@RequestBody Policy policy) {
		if (policy.getPolicyId() == null)
			return policyManager.insertPolicy(policy);
		else
			return policyManager.updatePolicy(policy);
	}

}
