package de.ollie.healthtracker.gui.swing.event;

import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Wraps a {@link MeatConsumptionService} and fires the {@link MeatConsumptionChangeNotifier} after every mutating call,
 * so views (e.g. the nutrition chart) can refresh themselves. Read-only calls are delegated unchanged.
 */
public class NotifyingMeatConsumptionService implements MeatConsumptionService {

	private final MeatConsumptionService delegate;
	private final MeatConsumptionChangeNotifier changeNotifier;

	public NotifyingMeatConsumptionService(
		MeatConsumptionService delegate,
		MeatConsumptionChangeNotifier changeNotifier
	) {
		this.delegate = delegate;
		this.changeNotifier = changeNotifier;
	}

	@Override
	public MeatConsumption createMeatConsumption(LocalDate dateOfRecording, MeatProduct meatProduct) {
		MeatConsumption created = delegate.createMeatConsumption(dateOfRecording, meatProduct);
		changeNotifier.notifyChanged();
		return created;
	}

	@Override
	public void deleteMeatConsumption(UUID id) {
		delegate.deleteMeatConsumption(id);
		changeNotifier.notifyChanged();
	}

	@Override
	public Optional<MeatConsumption> findById(UUID id) {
		return delegate.findById(id);
	}

	@Override
	public List<MeatConsumption> listMeatConsumptions() {
		return delegate.listMeatConsumptions();
	}

	@Override
	public MeatConsumption updateMeatConsumption(MeatConsumption toSave) {
		MeatConsumption saved = delegate.updateMeatConsumption(toSave);
		changeNotifier.notifyChanged();
		return saved;
	}
}
