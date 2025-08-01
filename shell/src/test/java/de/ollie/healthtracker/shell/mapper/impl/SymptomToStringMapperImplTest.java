package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.Symptom;
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
class SymptomToStringMapperImplTest {

	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 06, 25);
	private static final String DATE_OF_RECORDING_STR = "25.06.2025";
	private static final String DESCRIPTION = "description";
	private static final UUID ID = UUID.randomUUID();
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(8, 0);
	private static final String TIME_OF_RECORDING_STR = "08:00";

	@Mock
	private Symptom symptom;

	@InjectMocks
	private SymptomToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals("Date       Time  (ID)                                   Description", unitUnderTest.getHeadLine());
		}
	}

	@Nested
	class getUnderLine {

		@Test
		void returnsTheCorrectUnderLine() {
			assertEquals(
				"--------------------------------------------------------------------------------------------------",
				unitUnderTest.getUnderLine()
			);
		}
	}

	@Nested
	class map_Symptom {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingASymptomWithNoSetAttributes() {
			// Prepare
			String expected = "         -     - (                                null) -";
			// Run
			String returned = unitUnderTest.map(symptom);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingASymptomWithAllSetAttributes() {
			// Prepare
			String expected = DATE_OF_RECORDING_STR + " " + TIME_OF_RECORDING_STR + " (" + ID + ") " + DESCRIPTION;
			symptom =
				new Symptom()
					.setDateOfRecording(DATE_OF_RECORDING)
					.setDescription(DESCRIPTION)
					.setId(ID)
					.setTimeOfRecording(TIME_OF_RECORDING);
			// Run
			String returned = unitUnderTest.map(symptom);
			// Check
			assertEquals(expected, returned);
		}
	}
}
