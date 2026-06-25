package de.ollie.healthtracker.gui.swing.event;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Wraps a {@link BloodPressureMeasurementService} and fires the {@link BloodPressureMeasurementChangeNotifier} after
 * every mutating call, so views (e.g. the blood pressure chart) can refresh themselves. Read-only calls are delegated
 * unchanged.
 */
public class NotifyingBloodPressureMeasurementService implements BloodPressureMeasurementService {

	private final BloodPressureMeasurementService delegate;
	private final BloodPressureMeasurementChangeNotifier changeNotifier;

	public NotifyingBloodPressureMeasurementService(
		BloodPressureMeasurementService delegate,
		BloodPressureMeasurementChangeNotifier changeNotifier
	) {
		this.delegate = delegate;
		this.changeNotifier = changeNotifier;
	}

	@Override
	public BloodPressureMeasurement createBloodPressureMeasurement(
		String comment,
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		WhoBloodPressureClassification status,
		boolean irregularHeartbeat
	) {
		BloodPressureMeasurement created = delegate.createBloodPressureMeasurement(
			comment,
			dateOfRecording,
			diaMmHg,
			pulsePerMinute,
			sysMmHg,
			timeOfRecording,
			status,
			irregularHeartbeat
		);
		changeNotifier.notifyChanged();
		return created;
	}

	@Override
	public void deleteBloodPressureMeasurement(UUID id) {
		delegate.deleteBloodPressureMeasurement(id);
		changeNotifier.notifyChanged();
	}

	@Override
	public List<BloodPressureMeasurement> findAllBloodPressureMeasurementsByTimeInterval(LocalDate from, LocalDate to) {
		return delegate.findAllBloodPressureMeasurementsByTimeInterval(from, to);
	}

	@Override
	public List<BloodPressureMeasurement> findAllBloodPressureMeasurementsPrettifiedByTimeInterval(
		LocalDate from,
		LocalDate to
	) {
		return delegate.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(from, to);
	}

	@Override
	public Optional<BloodPressureMeasurement> findById(UUID id) {
		return delegate.findById(id);
	}

	@Override
	public List<BloodPressureMeasurement> listBloodPressureMeasurements() {
		return delegate.listBloodPressureMeasurements();
	}

	@Override
	public BloodPressureMeasurement updateBloodPressureMeasurement(BloodPressureMeasurement toSave) {
		BloodPressureMeasurement saved = delegate.updateBloodPressureMeasurement(toSave);
		changeNotifier.notifyChanged();
		return saved;
	}
}
