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
public class MedicationProduct {

	private UUID id;
	private LocalDate expirationDate;
	private Medication medication;
	private BigDecimal packageSize;
	private MedicationUnit medicationUnit;
}
