package de.ollie.healthtracker.core.service.model;

import java.math.BigDecimal;
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
public class WeightMeasurement {

	private UUID id;
	private String comment;
	private LocalDate dateOfRecording = LocalDate.now();
	private BigDecimal kg = new BigDecimal(70.0);
	private LocalTime timeOfRecording = LocalTime.now();
}
