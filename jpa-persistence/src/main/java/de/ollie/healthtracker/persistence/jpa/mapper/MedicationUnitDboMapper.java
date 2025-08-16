package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationUnitDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface MedicationUnitDboMapper {
	MedicationUnitDbo toDbo(MedicationUnit model);

	MedicationUnit toModel(MedicationUnitDbo dbo);
}
