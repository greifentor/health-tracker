package de.ollie.healthtracker.print.jasper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.WhoBloodPressureClassificationService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import de.ollie.healthtracker.print.jasper.po.BloodPressureMeasurementPO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementPOMapperTest {

	@Mock
	private WhoBloodPressureClassificationService whoBloodPressureClassificationService;

	@InjectMocks
	private BloodPressureMeasurementPOMapper unitUnderTest;

	private BloodPressureMeasurement measurement() {
		return new BloodPressureMeasurement()
			.setSysMmHg(125)
			.setDiaMmHg(82)
			.setPulsePerMinute(60)
			.setIrregularHeartbeat(true)
			.setDateOfRecording(LocalDate.of(2024, 3, 5))
			.setTimeOfRecording(LocalTime.of(7, 30));
	}

	private BloodPressureMeasurementPO mapSingle() {
		when(whoBloodPressureClassificationService.calculateClassification(anyInt(), anyInt()))
			.thenReturn(WhoBloodPressureClassification.NORMAL);
		return unitUnderTest.map(List.of(measurement())).get(0);
	}

	@Test
	void mapsTheSystolicValueAsString() {
		// Run
		BloodPressureMeasurementPO result = mapSingle();
		// Check
		assertEquals("125", result.getSysMmHg());
	}

	@Test
	void mapsTheDiastolicValueAsString() {
		// Run
		BloodPressureMeasurementPO result = mapSingle();
		// Check
		assertEquals("82", result.getDiaMmHg());
	}

	@Test
	void mapsThePulseValueAsString() {
		// Run
		BloodPressureMeasurementPO result = mapSingle();
		// Check
		assertEquals("60", result.getPulsePerMinute());
	}

	@Test
	void mapsTheIrregularHeartbeatFlag() {
		// Run
		BloodPressureMeasurementPO result = mapSingle();
		// Check
		assertEquals(true, result.isIrregularHeartbeat());
	}

	@Test
	void formatsTheDateAndTimeInGerman() {
		// Run
		BloodPressureMeasurementPO result = mapSingle();
		// Check
		assertEquals("05.03.2024 07:30", result.getDate());
	}

	@Test
	void translatesTheWhoClassificationToGerman() {
		// Prepare
		when(whoBloodPressureClassificationService.calculateClassification(anyInt(), anyInt()))
			.thenReturn(WhoBloodPressureClassification.HYPERTENSION_GRADE_1);
		// Run
		BloodPressureMeasurementPO result = unitUnderTest.map(List.of(measurement())).get(0);
		// Check
		assertEquals("Hypertonie Grad 1", result.getClassificationWho());
	}
}
