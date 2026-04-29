package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.NutritionClassCalculationService;
import de.ollie.healthtracker.core.service.model.NutritionCalculationData;
import de.ollie.healthtracker.core.service.model.NutritionClass;
import jakarta.inject.Named;

@Named
class NutritionClassCalculationServiceImpl implements NutritionClassCalculationService {

	@Override
	public NutritionClass calculate(NutritionCalculationData data) {
		if (data != null) {
			if (hasFishConsumption(data) && !hasMeatConsumption(data)) {
				return NutritionClass.PESCETARIAN;
			} else if (hasMeatConsumption(data)) {
				return NutritionClass.OMNIVOR;
			}
		}
		return NutritionClass.VEGETARIAN;
	}

	private boolean hasFishConsumption(NutritionCalculationData data) {
		return data.getFishConsumptionDays() > 0;
	}

	private boolean hasMeatConsumption(NutritionCalculationData data) {
		return data.getMeatConsumptionDays() > 0;
	}
}
