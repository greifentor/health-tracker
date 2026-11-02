package de.ollie.healthtracker.gui.swing.medicationproduct;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.MedicationProductService;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * Collects warning lines for all medication products whose expiration date lies before a reference date (the current day
 * by default), so they can be shown to the user on application start.
 */
@Named
@RequiredArgsConstructor
public class ExpiredMedicationProductProvider {

	private static final String UNKNOWN_MEDICATION = "-";

	private final MedicationProductService medicationProductService;

	public List<String> findExpiredProductWarnings() {
		return findExpiredProductWarnings(LocalDate.now());
	}

	List<String> findExpiredProductWarnings(LocalDate referenceDate) {
		return medicationProductService
			.listMedicationProducts()
			.stream()
			.filter(product -> isExpired(product, referenceDate))
			.map(this::toWarning)
			.toList();
	}

	private boolean isExpired(MedicationProduct product, LocalDate referenceDate) {
		return product.getExpirationDate() != null && product.getExpirationDate().isBefore(referenceDate);
	}

	private String toWarning(MedicationProduct product) {
		String medication = product.getMedication() != null && product.getMedication().getName() != null
			? product.getMedication().getName()
			: UNKNOWN_MEDICATION;
		return medication + " (abgelaufen am " + DateTimeUtil.DE_DATE_FORMAT.format(product.getExpirationDate()) + ")";
	}
}
