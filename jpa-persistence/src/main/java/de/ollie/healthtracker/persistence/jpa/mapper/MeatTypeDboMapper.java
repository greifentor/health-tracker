package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.MeatType;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatTypeDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface MeatTypeDboMapper {
	MeatType toModel(MeatTypeDbo dbo);

	MeatTypeDbo toDbo(MeatType model);
}
