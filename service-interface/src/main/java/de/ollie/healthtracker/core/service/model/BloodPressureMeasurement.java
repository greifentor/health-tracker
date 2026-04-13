package de.ollie.healthtracker.core.service.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Accessors(chain = true)
@Data
@Generated
public class BloodPressureMeasurement {

	private UUID id;
	private String comment;
	private LocalDate dateOfRecording = LocalDate.now();
	private int diaMmHg = 80;
	private int pulsePerMinute = 60;
	private int sysMmHg = 130;
	private LocalTime timeOfRecording = LocalTime.now();
	private BloodPressureMeasurementStatus status = BloodPressureMeasurementStatus.YELLOW;
	private boolean irregularHeartbeat;
}
