package de.ollie.healthtracker.gui.swing.chart.bloodpressure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementHistoryService;
import de.ollie.healthtracker.core.service.WhoBloodPressureClassificationService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureChartDataProviderTest {

	private static final int WINDOW_DAYS = 31;

	@Mock
	private BloodPressureMeasurementHistoryService bloodPressureMeasurementHistoryService;

	@Mock
	private WhoBloodPressureClassificationService whoBloodPressureClassificationService;

	@InjectMocks
	private BloodPressureChartDataProvider unitUnderTest;

	private BloodPressureMeasurement measurement(LocalDate date, int sys, int dia, int pulse) {
		return new BloodPressureMeasurement()
			.setDateOfRecording(date)
			.setSysMmHg(sys)
			.setDiaMmHg(dia)
			.setPulsePerMinute(pulse);
	}

	private void twoMeasurementsToday() {
		when(bloodPressureMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(List.of(measurement(LocalDate.now(), 120, 80, 60), measurement(LocalDate.now(), 130, 84, 66)));
	}

	@Test
	void averagesTheSystolicValuePerDay() {
		// Prepare
		twoMeasurementsToday();
		when(whoBloodPressureClassificationService.calculateClassification(anyInt(), anyInt()))
			.thenReturn(WhoBloodPressureClassification.NORMAL);
		// Run
		List<BloodPressureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(125, result.get(0).systolic());
	}

	@Test
	void averagesTheDiastolicValuePerDay() {
		// Prepare
		twoMeasurementsToday();
		when(whoBloodPressureClassificationService.calculateClassification(anyInt(), anyInt()))
			.thenReturn(WhoBloodPressureClassification.NORMAL);
		// Run
		List<BloodPressureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(82, result.get(0).diastolic());
	}

	@Test
	void averagesThePulseValuePerDay() {
		// Prepare
		twoMeasurementsToday();
		when(whoBloodPressureClassificationService.calculateClassification(anyInt(), anyInt()))
			.thenReturn(WhoBloodPressureClassification.NORMAL);
		// Run
		List<BloodPressureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(63, result.get(0).pulse());
	}

	@Test
	void setsTheWhoClassificationAsStatus() {
		// Prepare
		twoMeasurementsToday();
		when(whoBloodPressureClassificationService.calculateClassification(anyInt(), anyInt()))
			.thenReturn(WhoBloodPressureClassification.HIGH_NORMAL);
		// Run
		List<BloodPressureChartData> result = unitUnderTest.create(WINDOW_DAYS);
		// Check
		assertEquals(WhoBloodPressureClassification.HIGH_NORMAL, result.get(0).status());
	}

	@Test
	void classifiesTheAveragedSystolicAndDiastolicValue() {
		// Prepare
		twoMeasurementsToday();
		when(whoBloodPressureClassificationService.calculateClassification(anyInt(), anyInt()))
			.thenReturn(WhoBloodPressureClassification.NORMAL);
		// Run
		unitUnderTest.create(WINDOW_DAYS);
		// Check
		verify(whoBloodPressureClassificationService).calculateClassification(125, 82);
	}
}
