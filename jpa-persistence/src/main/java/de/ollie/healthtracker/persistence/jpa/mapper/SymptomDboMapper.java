package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.persistence.jpa.dbo.SymptomDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface SymptomDboMapper {
	SymptomDbo toDbo(Symptom model);

	Symptom toModel(SymptomDbo dbo);
}
