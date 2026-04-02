package de.ollie.healthtracker.core.service.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
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
public class MedicationLog {

	private UUID id;
	private boolean confirmed = true;
	private Medication medication;
	private MedicationUnit medicationUnit;
	private LocalDate dateOfIntake;
	private boolean selfMedication;
	private LocalTime timeOfIntake;
	private BigDecimal unitCount;

}