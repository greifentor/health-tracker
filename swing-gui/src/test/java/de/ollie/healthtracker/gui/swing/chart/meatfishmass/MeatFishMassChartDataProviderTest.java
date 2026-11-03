package de.ollie.healthtracker.gui.swing.chart.meatfishmass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.MeatConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.core.service.model.MeatType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MeatFishMassChartDataProviderTest {

	@Mock
	private MeatConsumptionHistoryService meatConsumptionHistoryService;

	@InjectMocks
	private MeatFishMassChartDataProvider unitUnderTest;

	private MeatConsumption consumption(LocalDate date, MeatCategory category, int amountInGr, double units) {
		return new MeatConsumption()
			.setDateOfRecording(date)
			.setUnits(BigDecimal.valueOf(units))
			.setMeatProduct(new MeatProduct().setAmountInGr(amountInGr).setMeatType(new MeatType().setCategory(category)));
	}

	@Test
	void sumsTheMeatMassOfTheMonthInKilograms() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(consumption(today, MeatCategory.MEAT, 250, 2.0), consumption(today, MeatCategory.MEAT, 100, 1.0))
			);
		// Run
		List<MeatFishMassChartData> result = unitUnderTest.create(1);
		// Check
		assertEquals(0.6, result.get(0).meatKilograms(), 0.0001);
	}

	@Test
	void sumsTheFishMassOfTheMonthInKilograms() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(consumption(today, MeatCategory.FISH, 150, 2.0)));
		// Run
		List<MeatFishMassChartData> result = unitUnderTest.create(1);
		// Check
		assertEquals(0.3, result.get(0).fishKilograms(), 0.0001);
	}

	@Test
	void returnsOneContiguousDataPointPerMonthOfTheWindow() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		// Run
		List<MeatFishMassChartData> result = unitUnderTest.create(3);
		// Check
		assertEquals(3, result.size());
	}
}
