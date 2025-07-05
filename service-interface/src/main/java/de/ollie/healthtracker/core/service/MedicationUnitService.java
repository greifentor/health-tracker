package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.MedicationUnit;
import java.util.List;
import java.util.UUID;

public interface MedicationUnitService {
	MedicationUnit createMedicationUnit(String name, String token);

	void deleteMedicationUnit(UUID id);

	List<MedicationUnit> listMedicationUnits();
}
