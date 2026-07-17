package de.ollie.healthtracker.gui.swing.chart.weight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.WeightMeasurementHistoryService;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeightChartDataProviderTest {

	private static final int WINDOW_DAYS = 31;

	@Mock
	private WeightMeasurementHistoryService weightMeasurementHistoryService;

	@InjectMocks
	private WeightChartDataProvider unitUnderTest;

	private WeightMeasurement measurement(LocalDate date, double kg) {
		return new WeightMeasurement().setDateOfRecording(date).setKg(BigDecimal.valueOf(kg));
	}

	@Test
	void averagesTheWeightOfTheSameDay() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(today, 80.0), measurement(today, 82.0)));
		// Run
		List<WeightChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(81.0, result.get(0).kg(), 0.0001);
	}

	@Test
	void createsOneDataPointPerDay() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(today, 80.0), measurement(today.minusDays(1), 79.0)));
		// Run
		List<WeightChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(2, result.size());
	}

	@Test
	void placesTodaysMeasurementAtTheLastWindowPosition() {
		// Prepare
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(LocalDate.now(), 80.0)));
		// Run
		List<WeightChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(WINDOW_DAYS, result.get(0).day());
	}

	@Test
	void placesThePreviousDayOneWindowPositionEarlier() {
		// Prepare
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(LocalDate.now().minusDays(1), 80.0)));
		// Run
		List<WeightChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(WINDOW_DAYS - 1, result.get(0).day());
	}

	@Test
	void ignoresMeasurementsWithoutWeight() {
		// Prepare
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(new WeightMeasurement().setDateOfRecording(LocalDate.now()).setKg(null)));
		// Run
		List<WeightChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertTrue(result.isEmpty());
	}

	@Test
	void returnsEmptyWhenThereAreNoMeasurements() {
		// Prepare
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		// Run
		List<WeightChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertTrue(result.isEmpty());
	}
}
