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
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NutritionChartDataProviderTest {

	private static final YearMonth CURRENT_MONTH = YearMonth.now();

	@Mock
	private MeatConsumptionHistoryService meatConsumptionHistoryService;

	@InjectMocks
	private NutritionChartDataProvider unitUnderTest;

	private MeatConsumption consumption(LocalDate date, MeatCategory category) {
		return new MeatConsumption()
			.setDateOfRecording(date)
			.setMeatProduct(new MeatProduct().setMeatType(new MeatType().setCategory(category)));
	}

	private NutritionChartData lastOf(List<NutritionChartData> result) {
		return result.get(result.size() - 1);
	}

	@Test
	void producesOneEntryPerMonthOfTheWindow() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		// Run
		List<NutritionChartData> result = unitUnderTest.create(3);
		// Check
		assertEquals(3, result.size());
	}

	@Test
	void supportsWindowsOfMoreThanTwelveMonths() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		// Run
		List<NutritionChartData> result = unitUnderTest.create(24);
		// Check
		assertEquals(24, result.size());
	}

	@Test
	void endsTheWindowWithTheCurrentMonth() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		// Run
		List<NutritionChartData> result = unitUnderTest.create(6);
		// Check
		assertEquals(CURRENT_MONTH.getMonthValue(), lastOf(result).month());
	}

	@Test
	void countsTheMeatDaysOfTheMonth() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(
					consumption(CURRENT_MONTH.atDay(1), MeatCategory.MEAT),
					consumption(CURRENT_MONTH.atDay(2), MeatCategory.MEAT)
				)
			);
		// Run
		List<NutritionChartData> result = unitUnderTest.create(1);
		// Check
		assertEquals(2, lastOf(result).meat());
	}

	@Test
	void countsTheFishDaysOfTheMonth() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(consumption(CURRENT_MONTH.atDay(1), MeatCategory.FISH)));
		// Run
		List<NutritionChartData> result = unitUnderTest.create(1);
		// Check
		assertEquals(1, lastOf(result).pescetarian());
	}

	@Test
	void computesTheVeggieDaysAsDaysInMonthMinusMeatAndFish() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(
					consumption(CURRENT_MONTH.atDay(1), MeatCategory.MEAT),
					consumption(CURRENT_MONTH.atDay(2), MeatCategory.MEAT),
					consumption(CURRENT_MONTH.atDay(3), MeatCategory.FISH)
				)
			);
		// Run
		List<NutritionChartData> result = unitUnderTest.create(1);
		// Check
		assertEquals(CURRENT_MONTH.lengthOfMonth() - 2 - 1, lastOf(result).veggie());
	}

	@Test
	void fillsMonthsWithoutConsumptionAsFullVeggie() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(consumption(CURRENT_MONTH.atDay(1), MeatCategory.MEAT)));
		// Run
		List<NutritionChartData> result = unitUnderTest.create(2);
		// Check
		assertEquals(CURRENT_MONTH.minusMonths(1).lengthOfMonth(), result.get(0).veggie());
	}
}
