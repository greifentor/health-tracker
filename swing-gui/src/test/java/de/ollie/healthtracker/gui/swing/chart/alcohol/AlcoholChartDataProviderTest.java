package de.ollie.healthtracker.gui.swing.chart.alcohol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.AlcoholConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlcoholChartDataProviderTest {

	private static final int WINDOW_DAYS = 31;

	@Mock
	private AlcoholConsumptionHistoryService alcoholConsumptionHistoryService;

	@InjectMocks
	private AlcoholChartDataProvider unitUnderTest;

	private AlcoholConsumption consumption(LocalDate date, double liter) {
		return new AlcoholConsumption().setDate(date).setLiter(BigDecimal.valueOf(liter));
	}

	@Test
	void sumsTheLitersOfTheSameDay() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(alcoholConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(consumption(today, 0.5), consumption(today, 0.3)));
		// Run
		List<AlcoholChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(0.8, result.get(0).liters(), 0.0001);
	}

	@Test
	void createsOneDataPointPerDay() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(alcoholConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(consumption(today, 0.5), consumption(today.minusDays(1), 0.3)));
		// Run
		List<AlcoholChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(2, result.size());
	}

	@Test
	void placesTodaysConsumptionAtTheLastWindowPosition() {
		// Prepare
		when(alcoholConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(consumption(LocalDate.now(), 0.5)));
		// Run
		List<AlcoholChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(WINDOW_DAYS, result.get(0).day());
	}

	@Test
	void placesThePreviousDayOneWindowPositionEarlier() {
		// Prepare
		when(alcoholConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(consumption(LocalDate.now().minusDays(1), 0.5)));
		// Run
		List<AlcoholChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(WINDOW_DAYS - 1, result.get(0).day());
	}

	@Test
	void returnsEmptyWhenThereAreNoConsumptions() {
		// Prepare
		when(alcoholConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		// Run
		List<AlcoholChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertTrue(result.isEmpty());
	}
}
