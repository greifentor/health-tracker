package de.ollie.healthtracker.gui.swing.event;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Wraps an {@link AlcoholConsumptionService} and fires the {@link AlcoholConsumptionChangeNotifier} after every mutating
 * call, so views (e.g. the alcohol chart) can refresh themselves. Read-only calls are delegated unchanged.
 */
public class NotifyingAlcoholConsumptionService implements AlcoholConsumptionService {

	private final AlcoholConsumptionService delegate;
	private final AlcoholConsumptionChangeNotifier changeNotifier;

	public NotifyingAlcoholConsumptionService(
		AlcoholConsumptionService delegate,
		AlcoholConsumptionChangeNotifier changeNotifier
	) {
		this.delegate = delegate;
		this.changeNotifier = changeNotifier;
	}

	@Override
	public AlcoholConsumption createAlcoholConsumption(
		LocalDate date,
		AlcoholProduct alcoholProduct,
		String comment,
		BigDecimal liter
	) {
		AlcoholConsumption created = delegate.createAlcoholConsumption(date, alcoholProduct, comment, liter);
		changeNotifier.notifyChanged();
		return created;
	}

	@Override
	public void deleteAlcoholConsumption(UUID id) {
		delegate.deleteAlcoholConsumption(id);
		changeNotifier.notifyChanged();
	}

	@Override
	public Optional<AlcoholConsumption> findById(UUID id) {
		return delegate.findById(id);
	}

	@Override
	public boolean isDuplicate(LocalDate date, AlcoholProduct alcoholProduct, BigDecimal liter) {
		return delegate.isDuplicate(date, alcoholProduct, liter);
	}

	@Override
	public List<AlcoholConsumption> listAlcoholConsumptions() {
		return delegate.listAlcoholConsumptions();
	}

	@Override
	public AlcoholConsumption updateAlcoholConsumption(AlcoholConsumption toSave) {
		AlcoholConsumption saved = delegate.updateAlcoholConsumption(toSave);
		changeNotifier.notifyChanged();
		return saved;
	}
}
