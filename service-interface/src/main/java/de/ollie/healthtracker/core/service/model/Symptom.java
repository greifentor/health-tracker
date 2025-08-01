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
public class Symptom {

	private UUID id;
	private String description;
	private LocalDate dateOfRecording;
	private LocalTime timeOfRecording;
}
