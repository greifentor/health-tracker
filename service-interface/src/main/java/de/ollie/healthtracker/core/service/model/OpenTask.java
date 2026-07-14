package de.ollie.healthtracker.core.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * An open task to be shown to the user. For now it only carries a human readable description.
 */
@Accessors(chain = true)
@Data
public class OpenTask {

	private String description;
}
