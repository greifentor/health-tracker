package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicationLogToStringMapperImplTest {

	private static final UUID ID = UUID.randomUUID();
	private static final LocalDate DATE = LocalDate.of(2025, 8, 1);
	private static final String DATE_STR = "01.08.2025";
	private static final String MEDICATION_NAME = "medication-name";
	private static final String MEDICATION_UNIT_TOKEN = "token";
	private static final LocalTime TIME = LocalTime.of(21, 31);
	private static final String TIME_STR = "21:31";
	private static final BigDecimal UNIT_COUNT = new BigDecimal(1.23);
	private static final String UNIT_COUNT_STR = "1,23";

	private Medication medication;

	@Mock
	private MedicationLog medicationLog;

	private MedicationUnit medicationUnit;

	@InjectMocks
	private MedicationLogToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals(
				"(ID)                                   Date       Time  Medication                     Unit      Count",
				unitUnderTest.getHeadLine()
			);
		}
	}

	@Nested
	class getUnderLine {

		@Test
		void returnsTheCorrectUnderLine() {
			assertEquals(
				"------------------------------------------------------------------------------------------------------",
				unitUnderTest.getUnderLine()
			);
		}
	}

	@Nested
	class map_Medication {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingMedicationWithNoSetAttributes() {
			// Prepare
			String expected =
				"(                                null) -          -     -                              -          0,00";
			// Run
			String returned = unitUnderTest.map(medicationLog);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingAManufacturerWithAllSetAttributes() {
			// Prepare
			String expected =
				"(" +
				ID +
				") " +
				DATE_STR +
				" " +
				TIME_STR +
				" " +
				MEDICATION_NAME +
				"                " +
				MEDICATION_UNIT_TOKEN +
				"      " +
				UNIT_COUNT_STR;
			medication = new Medication().setName(MEDICATION_NAME);
			medicationUnit = new MedicationUnit().setToken(MEDICATION_UNIT_TOKEN);
			medicationLog =
				new MedicationLog()
					.setDateOfIntake(DATE)
					.setId(ID)
					.setMedication(medication)
					.setMedicationUnit(medicationUnit)
					.setTimeOfIntake(TIME)
					.setUnitCount(UNIT_COUNT);
			// Run
			String returned = unitUnderTest.map(medicationLog);
			// Check
			assertEquals(expected, returned);
		}
	}
}
