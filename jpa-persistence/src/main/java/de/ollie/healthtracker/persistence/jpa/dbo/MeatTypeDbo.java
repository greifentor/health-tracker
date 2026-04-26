package de.ollie.healthtracker.persistence.jpa.dbo;

import de.ollie.healthtracker.core.service.model.MeatCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
@Entity(name = "MeatTypeDbo")
@Table(name = "MEAT_TYPE")
public class MeatTypeDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY", nullable = true)
	private MeatCategoryDbo category;

	@Column(name = "NAME", nullable = false)
	private String name;
}
