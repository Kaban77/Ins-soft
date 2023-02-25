package ru.demidov.insSoft.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.demidov.insSoft.db.CarDaoImpl;

@RestController
public class CarController {

	private final CarDaoImpl carManager;

	// private static final Logger logger =
	// LoggerFactory.getLogger(CarController.class);

	public CarController(CarDaoImpl carManager) {
		this.carManager = carManager;
	}

	@GetMapping(value = "/get-brands", produces = "application/json")
	public List<Map<String, Object>> getBrands(@RequestParam(value = "brand") String brandName) {
		return carManager.findBrands(brandName);
	}

	@GetMapping(value = "/get-models", produces = "application/json")
	public List<Map<String, Object>> getmodels(@RequestParam(value = "brandId") Integer brandId) {
		return carManager.findModels(brandId);
	}
}
