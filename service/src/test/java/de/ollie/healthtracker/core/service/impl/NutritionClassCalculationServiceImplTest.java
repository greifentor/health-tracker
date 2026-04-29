package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.healthtracker.core.service.model.NutritionCalculationData;
import de.ollie.healthtracker.core.service.model.NutritionClass;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NutritionClassCalculationServiceImplTest {

	@InjectMocks
	private NutritionClassCalculationServiceImpl unitUnderTest;

	@Nested
	class calculate_NutritionCalculationData {

		@Test
		void returnsNutritionClassVEGETARIAN_passingANullValue() {
			assertEquals(NutritionClass.VEGETARIAN, unitUnderTest.calculate(null));
		}

		@ParameterizedTest
		@CsvSource({ "-1", "0" })
		void returnsNutritionClassVEGETARIAN_passingADataObject_withMeatAndFishConsumptionDaysBothLesser1(
			int consumptionDays
		) {
			// Prepare
			NutritionCalculationData data = new NutritionCalculationData()
				.setFishConsumptionDays(consumptionDays)
				.setMeatConsumptionDays(consumptionDays);
			// Run & Check
			assertEquals(NutritionClass.VEGETARIAN, unitUnderTest.calculate(data));
		}

		@ParameterizedTest
		@CsvSource({ "1", "2", "42" })
		void returnsNutritionClassPESCETARIAN_passingADataObject_withMeatConsumptionDaysIsZero_butFishConsumptionDaysIsGreaterZero(
			int consumptionDays
		) {
			// Prepare
			NutritionCalculationData data = new NutritionCalculationData()
				.setFishConsumptionDays(consumptionDays)
				.setMeatConsumptionDays(0);
			// Run & Check
			assertEquals(NutritionClass.PESCETARIAN, unitUnderTest.calculate(data));
		}

		@ParameterizedTest
		@CsvSource({ "0", "1" })
		void returnsNutritionClassOMNIVOR_passingADataObject_withMeatConsumptionDaysGreaterZero_andFishConsumptionDaysZeroOrOne(
			int fishConsumptionDays
		) {
			// Prepare
			NutritionCalculationData data = new NutritionCalculationData()
				.setFishConsumptionDays(fishConsumptionDays)
				.setMeatConsumptionDays(1);
			// Run & Check
			assertEquals(NutritionClass.OMNIVOR, unitUnderTest.calculate(data));
		}
	}
}
