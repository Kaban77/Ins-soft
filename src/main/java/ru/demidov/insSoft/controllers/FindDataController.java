package ru.demidov.insSoft.controllers;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.demidov.insSoft.db.PolicyDaoImpl;
import ru.demidov.insSoft.objects.Policy;

@Controller
public class FindDataController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private JdbcTemplate jdbctemplate;

	@Autowired
	public void setDataSourse(DataSource dataSourse) {
		this.jdbctemplate = new JdbcTemplate(dataSourse);
	}

	@RequestMapping(value = "/get-policies", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Policy> getPolicies(@RequestParam(value = "dateFrom", required = false) String dateFrom, @RequestParam(value = "dateTo", required = false) String dateTo,
			@RequestParam(value = "policyNumber", required = false) String policyNumber) {

		PolicyForSearch policy = new PolicyForSearch(policyNumber, dateFrom, dateTo);

		return new PolicyDaoImpl(jdbctemplate).findPoliciesByParam(policy);
	}
}
