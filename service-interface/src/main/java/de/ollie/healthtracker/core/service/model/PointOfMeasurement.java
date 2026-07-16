package de.ollie.healthtracker.core.service.model;

import java.math.BigDecimal;
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
public class PointOfMeasurement {

	private UUID id;
	private String name;
	private BigDecimal regularMaxCelsius = new BigDecimal(37.0);
	private BigDecimal regularMinCelsius = new BigDecimal(37.0);
}
