package de.ollie.healthtracker.gui.swing.event;

import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Wraps a {@link BodyTemperatureMeasurementService} and fires the {@link BodyTemperatureMeasurementChangeNotifier} after
 * every mutating call, so views (e.g. the body temperature chart) can refresh themselves. Read-only calls are delegated
 * unchanged.
 */
public class NotifyingBodyTemperatureMeasurementService implements BodyTemperatureMeasurementService {

	private final BodyTemperatureMeasurementService delegate;
	private final BodyTemperatureMeasurementChangeNotifier changeNotifier;

	public NotifyingBodyTemperatureMeasurementService(
		BodyTemperatureMeasurementService delegate,
		BodyTemperatureMeasurementChangeNotifier changeNotifier
	) {
		this.delegate = delegate;
		this.changeNotifier = changeNotifier;
	}

	@Override
	public BodyTemperatureMeasurement createBodyTemperatureMeasurement(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal celsius,
		LocalTime timeOfRecording,
		PointOfMeasurement pointOfMeasurement
	) {
		BodyTemperatureMeasurement created = delegate.createBodyTemperatureMeasurement(
			comment,
			dateOfRecording,
			celsius,
			timeOfRecording,
			pointOfMeasurement
		);
		changeNotifier.notifyChanged();
		return created;
	}

	@Override
	public void deleteBodyTemperatureMeasurement(UUID id) {
		delegate.deleteBodyTemperatureMeasurement(id);
		changeNotifier.notifyChanged();
	}

	@Override
	public Optional<BodyTemperatureMeasurement> findById(UUID id) {
		return delegate.findById(id);
	}

	@Override
	public List<BodyTemperatureMeasurement> listBodyTemperatureMeasurements() {
		return delegate.listBodyTemperatureMeasurements();
	}

	@Override
	public BodyTemperatureMeasurement updateBodyTemperatureMeasurement(BodyTemperatureMeasurement toSave) {
		BodyTemperatureMeasurement saved = delegate.updateBodyTemperatureMeasurement(toSave);
		changeNotifier.notifyChanged();
		return saved;
	}
}
