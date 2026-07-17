package de.ollie.healthtracker.gui.swing.chart.bodytemperature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementHistoryService;
import de.ollie.healthtracker.core.service.BodyTemperatureStatusService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.model.BodyTemperatureStatus;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BodyTemperatureChartDataProviderTest {

	private static final int WINDOW_DAYS = 31;

	@Mock
	private BodyTemperatureMeasurementHistoryService bodyTemperatureMeasurementHistoryService;

	@Mock
	private BodyTemperatureStatusService bodyTemperatureStatusService;

	@InjectMocks
	private BodyTemperatureChartDataProvider unitUnderTest;

	private BodyTemperatureMeasurement measurement(LocalDate date, double celsius, PointOfMeasurement point) {
		return new BodyTemperatureMeasurement()
			.setDateOfRecording(date)
			.setCelsius(BigDecimal.valueOf(celsius))
			.setPointOfMeasurement(point);
	}

	@Test
	void averagesTheTemperaturePerDay() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(bodyTemperatureMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(today, 37.0, null), measurement(today, 37.2, null)));
		when(bodyTemperatureStatusService.calculateStatus(any(), any())).thenReturn(BodyTemperatureStatus.NORMAL);
		// Run
		List<BodyTemperatureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(37.1, result.get(0).celsius(), 0.0001);
	}

	@Test
	void placesTodaysMeasurementAtTheLastWindowPosition() {
		// Prepare
		when(bodyTemperatureMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(LocalDate.now(), 37.0, null)));
		when(bodyTemperatureStatusService.calculateStatus(any(), any())).thenReturn(BodyTemperatureStatus.NORMAL);
		// Run
		List<BodyTemperatureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(WINDOW_DAYS, result.get(0).day());
	}

	@Test
	void marksTheDayAboveWhenAnyMeasurementIsAboveItsRange() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(bodyTemperatureMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(today, 37.0, null), measurement(today, 38.0, null)));
		when(bodyTemperatureStatusService.calculateStatus(any(), any()))
			.thenReturn(BodyTemperatureStatus.NORMAL, BodyTemperatureStatus.ABOVE);
		// Run
		List<BodyTemperatureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(BodyTemperatureStatus.ABOVE, result.get(0).status());
	}

	@Test
	void marksTheDayBelowWhenAMeasurementIsBelowAndNoneIsAbove() {
		// Prepare
		LocalDate today = LocalDate.now();
		when(bodyTemperatureMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(today, 37.0, null), measurement(today, 36.0, null)));
		when(bodyTemperatureStatusService.calculateStatus(any(), any()))
			.thenReturn(BodyTemperatureStatus.NORMAL, BodyTemperatureStatus.BELOW);
		// Run
		List<BodyTemperatureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(BodyTemperatureStatus.BELOW, result.get(0).status());
	}

	@Test
	void delegatesTheClassificationToTheStatusService() {
		// Prepare
		PointOfMeasurement point = new PointOfMeasurement().setName("Oral");
		when(bodyTemperatureMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(LocalDate.now(), 37.6, point)));
		when(bodyTemperatureStatusService.calculateStatus(any(), any())).thenReturn(BodyTemperatureStatus.NORMAL);
		// Run
		unitUnderTest.create(WINDOW_DAYS);
		// Check
		verify(bodyTemperatureStatusService).calculateStatus(BigDecimal.valueOf(37.6), point);
	}

	@Test
	void returnsEmptyForNoData() {
		// Prepare
		when(bodyTemperatureMeasurementHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		// Run
		List<BodyTemperatureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertTrue(result.isEmpty());
	}
}
