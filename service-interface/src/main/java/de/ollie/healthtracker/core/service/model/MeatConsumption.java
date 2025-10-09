package de.ollie.healthtracker.core.service.model;

import java.time.LocalDate;
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
public class MeatConsumption {

	private UUID id;
	private LocalDate dateOfRecording;
	private String description;
}
