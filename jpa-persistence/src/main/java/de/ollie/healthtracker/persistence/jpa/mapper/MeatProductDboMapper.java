package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatProductDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface MeatProductDboMapper {
	MeatProduct toModel(MeatProductDbo dbo);

	MeatProductDbo toDbo(MeatProduct model);
}
