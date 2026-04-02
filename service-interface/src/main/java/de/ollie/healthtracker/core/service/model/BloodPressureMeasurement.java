package de.ollie.healthtracker.core.service.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

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
	private LocalDate dateOfRecording;
	private int diaMmHg;
	private int pulsePerMinute;
	private int sysMmHg;
	private LocalTime timeOfRecording;
	private BloodPressureMeasurementStatus status;
	private boolean irregularHeartbeat;

}