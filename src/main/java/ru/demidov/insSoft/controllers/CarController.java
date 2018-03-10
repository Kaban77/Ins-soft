package ru.demidov.insSoft.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.demidov.insSoft.db.CarDaoImpl;

@Controller
public class CarController {

	@Autowired
	private CarDaoImpl carManager;
	// private static final Logger logger =
	// LoggerFactory.getLogger(CarController.class);

	@RequestMapping(value = "/get-brands", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getBrands(@RequestParam(value = "brand") String brandName) {
		return carManager.findBrands(brandName);
	}

	@RequestMapping(value = "/get-models", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getmodels(@RequestParam(value = "brandId") Integer brandId) {
		return carManager.findModels(brandId);
	}
}
