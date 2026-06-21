package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholProductDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface AlcoholProductDboMapper {
	AlcoholProduct toModel(AlcoholProductDbo dbo);

	AlcoholProductDbo toDbo(AlcoholProduct model);
}
