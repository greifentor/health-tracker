package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HealthTrackingReportToStringMapperImplTest {

	private static final LocalDate FROM = LocalDate.of(2025, 8, 1);
	private static final String FROM_STR = "01.08.2025";
	private static final LocalDate TO = LocalDate.of(2025, 8, 31);
	private static final String TO_STR = "31.08.2025";

	@Mock
	private HealthTrackingReport report;

	@InjectMocks
	private HealthTrackingReportToStringMapperImpl unitUnderTest;

	@Nested
	class toString_HealthTrackingReport {

		@Test
		void throwsAnException_passingANullValueAsHealthTrackingReport() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.toString(null));
		}

		@Test
		void returnsACorrectString() {
			// Prepare
			String expected =
				HealthTrackingReportToStringMapperImpl.FROM +
				FROM_STR +
				"\n" +
				HealthTrackingReportToStringMapperImpl.TO +
				TO_STR;
			when(report.getFrom()).thenReturn(FROM);
			when(report.getTo()).thenReturn(TO);
			// Run
			String returned = unitUnderTest.toString(report);
			// Check
			assertEquals(expected, returned);
		}
	}
}
