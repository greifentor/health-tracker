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
@Entity(name = "MedicationProductDbo")
@Table(name = "MEDICATION_PRODUCT")
public class MedicationProductDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "EXPIRATION_DATE", nullable = false)
	private LocalDate expirationDate;

	@JoinColumn(name = "MEDICATION", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicationDbo medication;

	@Column(name = "PACKAGE_SIZE", nullable = false)
	private BigDecimal packageSize;

	@JoinColumn(name = "MEDICATION_UNIT", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicationUnitDbo medicationUnit;
}
