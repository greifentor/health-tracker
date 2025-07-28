package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.port.persistence.MedicationUnitPersistencePort;
import jakarta.inject.Named;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Named
@RequiredArgsConstructor
class MedicationUnitServiceImpl implements MedicationUnitService {

	private final MedicationUnitPersistencePort medicationUnitPersistencePort;

	@Override
	public MedicationUnit createMedicationUnit(String name, String token) {
		return medicationUnitPersistencePort.create(name, token);
	}

	@Override
	public void deleteMedicationUnit(UUID id) {
		medicationUnitPersistencePort.deleteById(id);
	}

	@Override
	public List<MedicationUnit> listMedicationUnits() {
		return medicationUnitPersistencePort.list();
	}
}
