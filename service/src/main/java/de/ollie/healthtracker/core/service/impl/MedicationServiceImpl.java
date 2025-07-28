package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.port.persistence.MedicationPersistencePort;
import jakarta.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
@RequiredArgsConstructor
class MedicationServiceImpl implements MedicationService {

	private final MedicationPersistencePort medicationPersistencePort;

	@Override
	public Medication createMedication(String name, Manufacturer manufacturer) {
		return medicationPersistencePort.create(name, manufacturer);
	}

	@Override
	public void deleteMedication(UUID id) {
		medicationPersistencePort.deleteById(id);
	}

	@Override
	public Optional<Medication> findByIdOrNameParticle(String namePartOrId) {
		return medicationPersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<Medication> listMedications() {
		return medicationPersistencePort.list();
	}
}
