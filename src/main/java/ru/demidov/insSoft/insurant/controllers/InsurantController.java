package ru.demidov.insSoft.insurant.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.demidov.insSoft.insurant.Insurant;
import ru.demidov.insSoft.insurant.InsurantManager;

@RestController
public class InsurantController {

	private final InsurantManager insutantManager;

	public InsurantController(InsurantManager insutantManager) {
		this.insutantManager = insutantManager;
	}

	@GetMapping(value = "/get-clients", produces = "application/json")
	public List<Insurant> getClients(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "patronymic", required = false) String patronymic,
			@RequestParam(value = "surname", required = false) String surname, @RequestParam(value = "birthDate", required = false) String birthDate) {
		Insurant insurant = new Insurant(surname, name, patronymic, birthDate);
		return insutantManager.findInsurants(insurant);
	}

	@PostMapping(value = "/save-insurant", produces = "application/json", consumes = "application/json")
	public Integer saveInsurant(@RequestBody Insurant insurant) {
		return insutantManager.createInsurant(insurant);
	}

}
