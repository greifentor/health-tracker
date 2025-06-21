package de.ollie.bptracker.core.service.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BloodPressureMeasurement {

	private LocalDate dateOfRecording;
	private int diaMmHg;
	private int pulsePerMinute;
	private int sysMmHg;
	private LocalTime timeOfRecording;
	private BloodPressureMeasurementStatus state;
}
