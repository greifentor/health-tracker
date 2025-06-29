package de.ollie.healthtracker.core.service.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BloodPressureMeasurement {

	private UUID id;
	private LocalDate dateOfRecording;
	private int diaMmHg;
	private int pulsePerMinute;
	private int sysMmHg;
	private LocalTime timeOfRecording;
	private BloodPressureMeasurementStatus status;
}
