package de.ollie.healthtracker.core.service.model;

import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class DoctorType {

	private UUID id;
	private String name;
}
