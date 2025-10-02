package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
import de.ollie.healthtracker.persistence.jpa.dbo.GeneralBodyPartDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface GeneralBodyPartDboMapper {
	GeneralBodyPart toModel(GeneralBodyPartDbo dbo);

	GeneralBodyPartDbo toDbo(GeneralBodyPart model);
}
