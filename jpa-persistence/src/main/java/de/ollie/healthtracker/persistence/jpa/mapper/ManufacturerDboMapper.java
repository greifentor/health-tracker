package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.persistence.jpa.dbo.ManufacturerDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface ManufacturerDboMapper {
	Manufacturer toModel(ManufacturerDbo dbo);

	ManufacturerDbo toDbo(Manufacturer model);
}
