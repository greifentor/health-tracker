package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.persistence.jpa.dbo.BodyPartDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface BodyPartDboMapper {
	BodyPart toModel(BodyPartDbo dbo);

	BodyPartDbo toDbo(BodyPart model);
}
