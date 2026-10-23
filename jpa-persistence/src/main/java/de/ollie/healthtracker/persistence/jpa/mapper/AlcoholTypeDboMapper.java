package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.AlcoholType;
import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholTypeDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface AlcoholTypeDboMapper {
	AlcoholType toModel(AlcoholTypeDbo dbo);

	AlcoholTypeDbo toDbo(AlcoholType model);
}
