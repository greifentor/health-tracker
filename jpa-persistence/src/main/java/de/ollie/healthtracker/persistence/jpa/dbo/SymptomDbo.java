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
@Entity(name = "SymptomDbo")
@Table(name = "SYMPTOM")
public class SymptomDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "DATE_OF_RECORDING", nullable = false)
	private LocalDate dateOfRecording;

	@JoinColumn(name = "BODY_PART", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private BodyPartDbo bodyPart;
}
