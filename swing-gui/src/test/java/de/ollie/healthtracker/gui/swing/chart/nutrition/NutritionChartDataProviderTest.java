package de.ollie.healthtracker.gui.swing.chart.nutrition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.MeatConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.core.service.model.MeatType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NutritionChartDataProviderTest {

	@Mock
	private MeatConsumptionHistoryService meatConsumptionHistoryService;

	@InjectMocks
	private NutritionChartDataProvider unitUnderTest;

	private MeatConsumption consumption(LocalDate date, MeatCategory category) {
		return new MeatConsumption()
			.setDateOfRecording(date)
			.setMeatProduct(new MeatProduct().setMeatType(new MeatType().setCategory(category)));
	}

	private List<MeatConsumption> marchConsumptions() {
		// fish only -> fish day; meat -> meat day; fish + meat -> meat day (not a fish day)
		return List.of(
			consumption(LocalDate.of(2024, 3, 1), MeatCategory.FISH),
			consumption(LocalDate.of(2024, 3, 2), MeatCategory.MEAT),
			consumption(LocalDate.of(2024, 3, 3), MeatCategory.FISH),
			consumption(LocalDate.of(2024, 3, 3), MeatCategory.MEAT)
		);
	}

	@Test
	void countsTheMeatDaysPerMonth() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(marchConsumptions());
		// Run
		List<NutritionChartData> result = unitUnderTest.create();
		// Check
		assertEquals(2, result.get(0).meat());
	}

	@Test
	void countsTheFishDaysPerMonth() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(marchConsumptions());
		// Run
		List<NutritionChartData> result = unitUnderTest.create();
		// Check
		assertEquals(1, result.get(0).pescetarian());
	}

	@Test
	void computesTheVeggieDaysAsDaysInMonthMinusMeatAndFish() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(marchConsumptions());
		// Run
		List<NutritionChartData> result = unitUnderTest.create();
		// Check
		assertEquals(31 - 2 - 1, result.get(0).veggie());
	}

	@Test
	void usesTheMonthValueForTheChartPoint() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(marchConsumptions());
		// Run
		List<NutritionChartData> result = unitUnderTest.create();
		// Check
		assertEquals(3, result.get(0).month());
	}

	@Test
	void sortsTheMonthsChronologically() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(
					consumption(LocalDate.of(2024, 5, 10), MeatCategory.MEAT),
					consumption(LocalDate.of(2024, 2, 10), MeatCategory.MEAT)
				)
			);
		// Run
		List<NutritionChartData> result = unitUnderTest.create();
		// Check
		assertEquals(List.of(2, 5), result.stream().map(NutritionChartData::month).toList());
	}
}
