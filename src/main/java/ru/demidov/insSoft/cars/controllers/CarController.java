package ru.demidov.insSoft.cars.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.demidov.insSoft.cars.CarManager;

@RestController
public class CarController {

	private final CarManager carManager;
	private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

	public CarController(CarManager carManager) {
		this.carManager = carManager;
	}

	@GetMapping(value = "/get-brands", produces = "application/json")
	public List<Map<String, Integer>> getBrands(@RequestParam(value = "brand") String brandName) {
		try {
			return carManager.findBrands(brandName);
		} catch (Exception e) {
			LOGGER.error("Error in get brands: brandName = " + brandName, e);
			throw new RuntimeException(e);
		}
	}

	@GetMapping(value = "/get-models", produces = "application/json")
	public List<Map<String, Integer>> getmodels(@RequestParam(value = "brandId") Integer brandId) {
		try {
			return carManager.findModels(brandId);
		} catch (Exception e) {
			LOGGER.error("Error in get models: brandId = " + brandId, e);
			throw new RuntimeException(e);
		}
	}
}
