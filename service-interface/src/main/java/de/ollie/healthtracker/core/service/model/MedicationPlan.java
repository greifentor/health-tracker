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
public class MedicationPlan {

	private UUID id;
	private LocalDate endDate;
	private Medication medication;
	private MedicationUnit medicationUnit;
	private LocalDate nextDateOfIntake;
	private boolean selfMedication;
	private LocalDate startDate;
	private LocalTime timeOfIntake;
	private BigDecimal unitCount;
}
