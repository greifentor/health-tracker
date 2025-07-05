package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.MedicationUnit;
import java.util.List;
import java.util.UUID;

public interface MedicationUnitPersistencePort {
	MedicationUnit create(String name, String token);

	void deleteById(UUID id);

	List<MedicationUnit> list();
}
