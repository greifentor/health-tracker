package de.ollie.healthtracker.gui.swing.medicationproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.MedicationProductService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExpiredMedicationProductProviderTest {

	private static final LocalDate REFERENCE_DATE = LocalDate.of(2026, 11, 2);

	@Mock
	private MedicationProductService medicationProductService;

	@InjectMocks
	private ExpiredMedicationProductProvider unitUnderTest;

	@BeforeEach
	void setUp() {
		when(medicationProductService.listMedicationProducts()).thenReturn(List.of());
	}

	private MedicationProduct medicationProduct(String medicationName, LocalDate expirationDate) {
		return new MedicationProduct()
			.setMedication(new Medication().setName(medicationName))
			.setExpirationDate(expirationDate);
	}

	@Test
	void listsAWarning_forAProductExpiredBeforeTheReferenceDate() {
		// Prepare
		when(medicationProductService.listMedicationProducts())
			.thenReturn(List.of(medicationProduct("Aspirin", LocalDate.of(2026, 10, 31))));
		// Run
		List<String> result = unitUnderTest.findExpiredProductWarnings(REFERENCE_DATE);
		// Check
		assertEquals(List.of("Aspirin (abgelaufen am 31.10.2026)"), result);
	}

	@Test
	void listsNoWarning_forAProductExpiringAfterTheReferenceDate() {
		// Prepare
		when(medicationProductService.listMedicationProducts())
			.thenReturn(List.of(medicationProduct("Aspirin", LocalDate.of(2026, 11, 3))));
		// Run
		List<String> result = unitUnderTest.findExpiredProductWarnings(REFERENCE_DATE);
		// Check
		assertTrue(result.isEmpty());
	}

	@Test
	void listsNoWarning_forAProductExpiringOnTheReferenceDate() {
		// Prepare
		when(medicationProductService.listMedicationProducts())
			.thenReturn(List.of(medicationProduct("Aspirin", REFERENCE_DATE)));
		// Run
		List<String> result = unitUnderTest.findExpiredProductWarnings(REFERENCE_DATE);
		// Check
		assertTrue(result.isEmpty());
	}
}
