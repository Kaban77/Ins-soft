package ru.demidov.insSoft.interfaces;

import java.util.List;
import java.util.Map;

public interface CarDao {

	List<Map<String, Object>> findBrands(String brandName);

	List<Map<String, Object>> findModels(Integer brandId);
}
