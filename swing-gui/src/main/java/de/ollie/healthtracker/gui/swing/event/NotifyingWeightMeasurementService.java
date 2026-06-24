package de.ollie.healthtracker.gui.swing.event;

import de.ollie.healthtracker.core.service.WeightMeasurementService;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Wraps a {@link WeightMeasurementService} and fires the {@link WeightMeasurementChangeNotifier} after every mutating
 * call, so views (e.g. the weight chart) can refresh themselves. Read-only calls are delegated unchanged.
 */
public class NotifyingWeightMeasurementService implements WeightMeasurementService {

	private final WeightMeasurementService delegate;
	private final WeightMeasurementChangeNotifier changeNotifier;

	public NotifyingWeightMeasurementService(
		WeightMeasurementService delegate,
		WeightMeasurementChangeNotifier changeNotifier
	) {
		this.delegate = delegate;
		this.changeNotifier = changeNotifier;
	}

	@Override
	public WeightMeasurement createWeightMeasurement(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal kg,
		LocalTime timeOfRecording
	) {
		WeightMeasurement created = delegate.createWeightMeasurement(comment, dateOfRecording, kg, timeOfRecording);
		changeNotifier.notifyChanged();
		return created;
	}

	@Override
	public void deleteWeightMeasurement(UUID id) {
		delegate.deleteWeightMeasurement(id);
		changeNotifier.notifyChanged();
	}

	@Override
	public Optional<WeightMeasurement> findById(UUID id) {
		return delegate.findById(id);
	}

	@Override
	public List<WeightMeasurement> listWeightMeasurements() {
		return delegate.listWeightMeasurements();
	}

	@Override
	public WeightMeasurement updateWeightMeasurement(WeightMeasurement toSave) {
		WeightMeasurement saved = delegate.updateWeightMeasurement(toSave);
		changeNotifier.notifyChanged();
		return saved;
	}
}
