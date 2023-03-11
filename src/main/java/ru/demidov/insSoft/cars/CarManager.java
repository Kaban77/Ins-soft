package ru.demidov.insSoft.cars;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ru.demidov.insSoft.cars.brands.BrandRepository;

@Component
public class CarManager {

	private final BrandRepository brandRepository;
	private final CarRepository carRepository;

	public CarManager(BrandRepository brandRepository, CarRepository carRepository) {
		this.brandRepository = brandRepository;
		this.carRepository = carRepository;
	}

	public List<Map<String, Integer>> findBrands(String brandName) {
		var brands = brandRepository.search(brandName);
		if (brands == null) {
			return List.of();
		}

		return brands.stream()
				.map(br -> Map.of(br.getName(), br.getId()))
				.collect(Collectors.toList());
	}

	public List<Map<String, Integer>> findModels(Integer brandId) {
		var cars = carRepository.getByBrand(brandId);

		if (cars == null) {
			return List.of();
		}

		return cars.stream()
				.map(car -> Map.of(car.getModelName(), car.getModelId()))
				.collect(Collectors.toList());
	}
}
