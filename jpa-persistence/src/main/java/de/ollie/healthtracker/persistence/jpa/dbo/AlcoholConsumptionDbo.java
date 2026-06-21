package de.ollie.healthtracker.persistence.jpa.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity(name = "AlcoholConsumptionDbo")
@Table(name = "ALCOHOL_CONSUMPTION")
public class AlcoholConsumptionDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "DATE", nullable = false)
	private LocalDate date;

	@JoinColumn(name = "ALCOHOL_PRODUCT", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private AlcoholProductDbo alcoholProduct;

	@Column(name = "COMMENT", nullable = false)
	private String comment;
}
