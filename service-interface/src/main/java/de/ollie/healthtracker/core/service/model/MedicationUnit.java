package de.ollie.healthtracker.core.service.model;

import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class MedicationUnit {

	private UUID id;
	private String name;
	private String token;
}
