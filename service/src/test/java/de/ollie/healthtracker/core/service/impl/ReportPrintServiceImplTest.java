package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.ReportService;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import de.ollie.healthtracker.core.service.port.print.PrintPort;
import de.ollie.healthtracker.core.service.port.print.PrintPort.Details;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ReportPrintServiceImplTest {

	private static final LocalDate FROM = LocalDate.of(2025, 8, 1);
	private static final String ID_0 = "id0";
	private static final String ID_1 = "id1";
	private static final LocalDate TO = LocalDate.of(2025, 8, 31);

	private static final Details DETAILS_0 = new Details(ID_0, "description");
	private static final Details DETAILS_1 = new Details(ID_1, "description");

	@Mock
	private HealthTrackingReport healthTrackerReport;

	@Mock
	private Map<String, String> parameters;

	@Mock
	private PrintPort printPort0;

	@Mock
	private PrintPort printPort1;

	@Mock
	private ReportService reportService;

	@InjectMocks
	private ReportPrintServiceImpl unitUnderTest;

	@Nested
	class postConstruct {

		@Test
		void createsAnEmptyMap_whenNoPrintPortsAreFound() {
			// Prepare
			List<PrintPort> pps = List.of();
			ReflectionTestUtils.setField(unitUnderTest, "printPortList", pps);
			// Run
			unitUnderTest.postConstruct();
			// Check
			assertTrue(unitUnderTest.getDetails().isEmpty());
		}

		@Test
		void createsAMap_withPrintPortsFromPrintPortList() {
			// Prepare
			List<PrintPort> pps = List.of(printPort1, printPort0);
			ReflectionTestUtils.setField(unitUnderTest, "printPortList", pps);
			when(printPort0.getDetails()).thenReturn(DETAILS_0);
			when(printPort1.getDetails()).thenReturn(DETAILS_1);
			// Run
			unitUnderTest.postConstruct();
			// Check
			assertEquals(2, unitUnderTest.getDetails().size());
			assertEquals(ID_0, unitUnderTest.getDetails().get(0).getId());
			assertEquals(ID_1, unitUnderTest.getDetails().get(1).getId());
		}
	}

	@Nested
	class printForTimeInterval_LocalDate_LocalDate_String_MapStringString {

		@Test
		void throwsAnException_passingANullValue_asFrom() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.printForTimeInterval(null, TO, ID_0, parameters)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asParameters() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.printForTimeInterval(FROM, TO, ID_0, null));
		}

		@Test
		void throwsAnException_passingANullValue_asPrintPortId() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.printForTimeInterval(FROM, TO, null, parameters)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asTo() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.printForTimeInterval(FROM, null, ID_0, parameters)
			);
		}

		@Test
		void throwsAnException_whenPrintPortId_isNotExisting() {
			// Prepare
			Map<String, PrintPort> pps = Map.of(ID_0, printPort1, ID_1, printPort0);
			ReflectionTestUtils.setField(unitUnderTest, "printPorts", pps);
			// Run & Check
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.printForTimeInterval(FROM, TO, ID_0 + 1, parameters)
			);
		}

		@Test
		void happyRun() {
			// Prepare
			Map<String, PrintPort> pps = Map.of(ID_0, printPort1, ID_1, printPort0);
			ReflectionTestUtils.setField(unitUnderTest, "printPorts", pps);
			when(reportService.collectData(FROM, TO)).thenReturn(healthTrackerReport);
			// Run
			unitUnderTest.printForTimeInterval(FROM, TO, ID_0, parameters);
			// Check
			verify(printPort1, times(1)).print(healthTrackerReport, parameters);
		}
	}
}
