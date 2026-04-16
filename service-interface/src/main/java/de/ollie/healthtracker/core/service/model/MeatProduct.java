package de.ollie.healthtracker.core.service.model;

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
public class MeatProduct implements NameProvider<MeatProduct> {

	private UUID id;
	private int amountInGr;
	private String description;
	private MeatType meatType;

	public String getName() {
		return getDescription();
	}

	public MeatProduct setName(String newName) {
		setDescription(newName);
		return this;
	}
}
