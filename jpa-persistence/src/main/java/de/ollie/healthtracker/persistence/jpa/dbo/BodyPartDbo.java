package de.ollie.healthtracker.persistence.jpa.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity(name = "BodyPartDbo")
@Table(name = "BODY_PART")
public class BodyPartDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@JoinColumn(name = "GENERAL_BODY_PART", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private GeneralBodyPartDbo generalBodyPart;

	@Column(name = "NAME", nullable = false)
	private String name;
}
