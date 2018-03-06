package ru.demidov.insSoft.controllers;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.demidov.insSoft.db.InsertDaoImpl;
import ru.demidov.insSoft.db.SelectDaoImpl;
import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.Insurant;
import ru.demidov.insSoft.objects.Policy;
import ru.demidov.insSoft.objects.PolicyForSearch;
import ru.demidov.insSoft.objects.PolicyToDB;

@Controller
public class DataBaseController {

	private JdbcTemplate jdbctemplate;

	// private static final Logger logger =
	// LoggerFactory.getLogger(DataBaseController.class);

	@Autowired
	public void setDataSourse(DataSource dataSourse) {
		this.jdbctemplate = new JdbcTemplate(dataSourse);
	}

	@RequestMapping(value = "/get-policies", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Policy> getPolicies(@RequestParam(value = "dateFrom", required = false) String dateFrom, @RequestParam(value = "dateTo", required = false) String dateTo,
			@RequestParam(value = "policyNumber", required = false) String policyNumber) {

		PolicyForSearch policy = new PolicyForSearch(policyNumber, dateFrom, dateTo);

		return new SelectDaoImpl(jdbctemplate).findPoliciesByParam(policy);
	}

	@RequestMapping(value = "/get-clients", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Insurant> getClients(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "patronymic", required = false) String patronymic,
			@RequestParam(value = "surname", required = false) String surname, @RequestParam(value = "birthDate", required = false) String birthDate) {

		Insurant insurant = new Insurant(surname, name, patronymic, birthDate);

		return new SelectDaoImpl(jdbctemplate).findInsurants(insurant);
	}

	@RequestMapping(value = "/get-brands", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getBrands(@RequestParam(value = "brand") String brandName) {
		return new SelectDaoImpl(jdbctemplate).findBrands(brandName);
	}

	@RequestMapping(value = "/get-models", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getmodels(@RequestParam(value = "brandId") Integer brandId) {
		return new SelectDaoImpl(jdbctemplate).findModels(brandId);
	}

	@RequestMapping(value = "/save-insurant", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveInsurant(@RequestBody Insurant insurant) {

		return new InsertDaoImpl(jdbctemplate).createInsurant(insurant);
	}

	@RequestMapping(value = "/save-policy", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	public Coefficients savePolicy(@RequestBody PolicyToDB policy) {

		if (policy.getPolicyId() == null)

			return new InsertDaoImpl(jdbctemplate).insertPolicy(policy);
		else

			return new InsertDaoImpl(jdbctemplate).updatePolicy(policy);

	}
}
