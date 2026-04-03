package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.port.persistence.ManufacturerPersistencePort;
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
class ManufacturerServiceImpl implements ManufacturerService {

	private final ManufacturerPersistencePort manufacturerPersistencePort;

	@Override
	public Manufacturer createManufacturer(String name) {
		return manufacturerPersistencePort.create(name);
	}

	@Override
	public void deleteManufacturer(UUID id) {
		manufacturerPersistencePort.deleteById(id);
	}

	@Override
	public Optional<Manufacturer> findById(UUID id) {
		return manufacturerPersistencePort.findById(id);
	}

	@Override
	public Optional<Manufacturer> findByIdOrNameParticle(String namePartOrId) {
		return manufacturerPersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<Manufacturer> listManufacturers() {
		return manufacturerPersistencePort.list();
	}

	@Override
	public Manufacturer updateManufacturer(Manufacturer toSave) {
		return manufacturerPersistencePort.update(toSave);
	}
}
