package de.ollie.bptracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.bptracker.core.service.UuidFactory;
import de.ollie.bptracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.bptracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DboFactory {

	private final UuidFactory uuidFactory;

	BloodPressureMeasurementDbo create(
		int sysMmHg,
		int diaMmHg,
		int pulsePerMinute,
		BloodPressureMeasurementStatusDbo state,
		LocalDate dateOfRecording,
		LocalTime timeOfRecording
	) {
		ensure(dateOfRecording != null, "Date of recording cannot be null!");
		ensure(diaMmHg > 0, "DiaMmHg cannot be lesser then 1!");
		ensure(pulsePerMinute > 0, "PulsePerMinute cannot be lesser then 1!");
		ensure(state != null, "State cannot be null!");
		ensure(sysMmHg > 0, "SysMmHg cannot be lesser then 1!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		ensure(timeOfRecording != null, "time of recording cannot be null!");
		return new BloodPressureMeasurementDbo()
			.setDateOfRecording(dateOfRecording)
			.setDiaMmHg(diaMmHg)
			.setId(uuidFactory.create())
			.setPulsePerMinute(pulsePerMinute)
			.setStatus(state)
			.setSysMmHg(sysMmHg)
			.setTimeOfRecording(timeOfRecording);
	}
}
