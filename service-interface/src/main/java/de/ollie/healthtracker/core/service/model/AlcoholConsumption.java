package de.ollie.healthtracker.core.service.model;

import java.math.BigDecimal;
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
public class AlcoholConsumption {

	private UUID id;
	private LocalDate date;
	private AlcoholProduct alcoholProduct;
	private String comment;
	private BigDecimal liter;
}
