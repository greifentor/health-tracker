package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.core.service.port.persistence.SymptomPersistencePort;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SymptomServiceImplTest {

	private static final LocalDate LOCAL_DATE = LocalDate.of(2025, 10, 15);

	@Mock
	private SymptomPersistencePort symptomPersistencePort;

	@Mock
	private UuidFactory uuidFactory;

	@InjectMocks
	private SymptomServiceImpl unitUnderTest;

	@Nested
	class duplicateNewestSymptomEntries {

		@Test
		void returnsTheCountOfDuplicatedRecords() {
			// Prepare
			Symptom symptom0 = spy(new Symptom());
			Symptom symptom1 = spy(new Symptom());
			List<Symptom> symptomsToChange = List.of(symptom0, symptom1);
			when(symptomPersistencePort.getMaxDateOfRecording()).thenReturn(LOCAL_DATE);
			when(symptomPersistencePort.findAllByDateOfRecording(LOCAL_DATE)).thenReturn(symptomsToChange);
			// Run
			int returned = unitUnderTest.duplicateNewestSymptomEntries();
			// Check
			assertEquals(symptomsToChange.size(), returned);
		}

		@Test
		void setsNullForEachFoundSymptom() {
			// Prepare
			Symptom symptom0 = spy(new Symptom());
			Symptom symptom1 = spy(new Symptom());
			UUID uuid0 = mock(UUID.class);
			UUID uuid1 = mock(UUID.class);
			List<Symptom> symptomsToChange = List.of(symptom0, symptom1);
			when(symptomPersistencePort.getMaxDateOfRecording()).thenReturn(LOCAL_DATE);
			when(symptomPersistencePort.findAllByDateOfRecording(LOCAL_DATE)).thenReturn(symptomsToChange);
			when(uuidFactory.create()).thenReturn(uuid0, uuid1);
			// Run
			unitUnderTest.duplicateNewestSymptomEntries();
			// Check
			verify(symptom0, times(1)).setId(uuid0);
			verify(symptom1, times(1)).setId(uuid1);
		}

		@Test
		void setsANewDateForEachFoundSymptom() {
			// Prepare
			Symptom symptom0 = spy(new Symptom());
			Symptom symptom1 = spy(new Symptom());
			List<Symptom> symptomsToChange = List.of(symptom0, symptom1);
			when(symptomPersistencePort.getMaxDateOfRecording()).thenReturn(LOCAL_DATE);
			when(symptomPersistencePort.findAllByDateOfRecording(LOCAL_DATE)).thenReturn(symptomsToChange);
			// Run
			unitUnderTest.duplicateNewestSymptomEntries();
			// Check
			verify(symptom0, times(1)).setDateOfRecording(LOCAL_DATE.plusDays(1));
			verify(symptom1, times(1)).setDateOfRecording(LOCAL_DATE.plusDays(1));
		}
	}
}
