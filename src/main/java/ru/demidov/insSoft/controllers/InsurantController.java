package ru.demidov.insSoft.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.demidov.insSoft.db.InsurantDaoImpl;
import ru.demidov.insSoft.objects.Insurant;

@Controller
public class InsurantController {

	@Autowired
	private InsurantDaoImpl insutantManager;

	// private static final Logger logger =
	// LoggerFactory.getLogger(InsurantController.class);

	@RequestMapping(value = "/get-clients", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Insurant> getClients(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "patronymic", required = false) String patronymic,
			@RequestParam(value = "surname", required = false) String surname, @RequestParam(value = "birthDate", required = false) String birthDate) {

		Insurant insurant = new Insurant(surname, name, patronymic, birthDate);

		return insutantManager.findInsurants(insurant);
	}

	@RequestMapping(value = "/save-insurant", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	// @Transactional(propagation = Propagation.REQUIRED)
	public Integer saveInsurant(@RequestBody Insurant insurant) {

		return insutantManager.createInsurant(insurant);
	}

}
