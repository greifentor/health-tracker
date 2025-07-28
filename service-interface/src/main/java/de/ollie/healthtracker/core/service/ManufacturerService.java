package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;

@Generated
public interface ManufacturerService {
	Manufacturer createManufacturer(String name);

	void deleteManufacturer(UUID id);

	Optional<Manufacturer> findByIdOrNameParticle(String namePartOrId);

	List<Manufacturer> listManufacturers();
}
