package de.ollie.healthtracker.persistence.jpa.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity(name = "MedicationLogDbo")
@Table(name = "MEDICATION_LOG")
public class MedicationLogDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@JoinColumn(name = "MEDICATION", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicationDbo medication;

	@JoinColumn(name = "MEDICATION_UNIT", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicationUnitDbo medicationUnit;

	@Column(name = "DATE_OF_INTAKE", nullable = false)
	private LocalDate dateOfIntake;

	@Column(name = "SELF_MEDICATION", nullable = true)
	private boolean selfMedication;

	@Column(name = "TIME_OF_INTAKE", nullable = false)
	private LocalTime timeOfIntake;

	@Column(name = "UNIT_COUNT", nullable = false)
	private BigDecimal unitCount;
}
