package de.ollie.healthtracker.persistence.jpa.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity(name = "PointOfMeasurementDbo")
@Table(name = "POINT_OF_MEASUREMENT")
public class PointOfMeasurementDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "REGULAR_MAX_CELSIUS", nullable = true)
	private BigDecimal regularMaxCelsius;

	@Column(name = "REGULAR_MIN_CELSIUS", nullable = true)
	private BigDecimal regularMinCelsius;
}
