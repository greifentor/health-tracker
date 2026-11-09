package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.LongTermSymptom;
import de.ollie.healthtracker.persistence.jpa.dbo.LongTermSymptomDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface LongTermSymptomDboMapper {
	LongTermSymptom toModel(LongTermSymptomDbo dbo);

	LongTermSymptomDbo toDbo(LongTermSymptom model);
}
