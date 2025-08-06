package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.port.print.PrintPort;
import de.ollie.healthtracker.core.service.port.print.PrintPort.Details;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ReportPrintServiceImplTest {

	private static final String ID_0 = "id0";
	private static final String ID_1 = "id1";
	private static final Details DETAILS_0 = new Details(ID_0, "description");
	private static final Details DETAILS_1 = new Details(ID_1, "description");

	@Mock
	private PrintPort printPort0;

	@Mock
	private PrintPort printPort1;

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
}
