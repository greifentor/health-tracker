package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.NutritionCalculationData;
import de.ollie.healthtracker.core.service.model.NutritionClass;

public interface NutritionClassCalculationService {
	NutritionClass calculate(NutritionCalculationData data);
}
