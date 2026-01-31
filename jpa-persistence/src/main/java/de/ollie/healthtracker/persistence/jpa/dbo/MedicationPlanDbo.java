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
@Entity(name = "MedicationPlanDbo")
@Table(name = "MEDICATION_PLAN")
public class MedicationPlanDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "END_DATE", nullable = false)
	private LocalDate endDate;

	@JoinColumn(name = "MEDICATION", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicationDbo medication;

	@JoinColumn(name = "MEDICATION_UNIT", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicationUnitDbo medicationUnit;

	@Column(name = "NEXT_DATE_OF_INTAKE", nullable = false)
	private LocalDate nextDateOfIntake;

	@Column(name = "SELF_MEDICATION", nullable = true)
	private boolean selfMedication;

	@Column(name = "START_DATE", nullable = false)
	private LocalDate startDate;

	@Column(name = "TIME_OF_INTAKE", nullable = false)
	private LocalTime timeOfIntake;

	@Column(name = "UNIT_COUNT", nullable = false)
	private BigDecimal unitCount;
}
