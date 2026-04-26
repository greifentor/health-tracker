package de.ollie.healthtracker.core.service.model;

import de.ollie.healthtracker.core.service.model.MeatCategory;
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
public class MeatType {

	private UUID id;
	private MeatCategory category;
	private String name;
}
