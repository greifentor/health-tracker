package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WhoBloodPressureClassificationServiceImplTest {

	@InjectMocks
	private WhoBloodPressureClassificationServiceImpl unitUnderTest;

	@Nested
	class calculateClassificationIntInt {

		@ParameterizedTest
		@CsvSource(
			{
				"119,79,OPTIMAL",
				"120,80,NORMAL",
				"129,84,NORMAL",
				"119,84,NORMAL",
				"124,79,NORMAL",
				"129,85,HIGH_NORMAL",
				"130,84,HIGH_NORMAL",
				"130,85,HIGH_NORMAL",
				"139,89,HIGH_NORMAL",
				"139,90,HYPERTENSION_GRADE_1",
				"140,89,HYPERTENSION_GRADE_1",
				"140,90,HYPERTENSION_GRADE_1",
				"159,99,HYPERTENSION_GRADE_1",
				"159,100,HYPERTENSION_GRADE_2",
				"160,99,HYPERTENSION_GRADE_2",
				"160,100,HYPERTENSION_GRADE_2",
				"179,109,HYPERTENSION_GRADE_2",
				"179,110,HYPERTENSION_GRADE_3",
				"180,109,HYPERTENSION_GRADE_3",
				"180,110,HYPERTENSION_GRADE_3",
			}
		)
		void returnsTheCorrectClassification(int sysMmHg, int diaMmHg, WhoBloodPressureClassification expected) {
			assertEquals(expected, unitUnderTest.calculateClassification(sysMmHg, diaMmHg));
		}
	}
}
