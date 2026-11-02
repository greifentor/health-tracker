package de.ollie.healthtracker.gui.swing.event;

import de.ollie.healthtracker.core.service.MedicationProductService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Wraps a {@link MedicationProductService} and fires the {@link MedicationProductChangeNotifier} after every mutating
 * call, so the expired medication product overview can refresh itself. Read-only calls are delegated unchanged.
 */
public class NotifyingMedicationProductService implements MedicationProductService {

	private final MedicationProductService delegate;
	private final MedicationProductChangeNotifier changeNotifier;

	public NotifyingMedicationProductService(
		MedicationProductService delegate,
		MedicationProductChangeNotifier changeNotifier
	) {
		this.delegate = delegate;
		this.changeNotifier = changeNotifier;
	}

	@Override
	public MedicationProduct createMedicationProduct(
		LocalDate expirationDate,
		Medication medication,
		BigDecimal packageSize,
		MedicationUnit medicationUnit
	) {
		MedicationProduct created = delegate.createMedicationProduct(
			expirationDate,
			medication,
			packageSize,
			medicationUnit
		);
		changeNotifier.notifyChanged();
		return created;
	}

	@Override
	public void deleteMedicationProduct(UUID id) {
		delegate.deleteMedicationProduct(id);
		changeNotifier.notifyChanged();
	}

	@Override
	public Optional<MedicationProduct> findById(UUID id) {
		return delegate.findById(id);
	}

	@Override
	public List<MedicationProduct> listMedicationProducts() {
		return delegate.listMedicationProducts();
	}

	@Override
	public MedicationProduct updateMedicationProduct(MedicationProduct toSave) {
		MedicationProduct saved = delegate.updateMedicationProduct(toSave);
		changeNotifier.notifyChanged();
		return saved;
	}
}
