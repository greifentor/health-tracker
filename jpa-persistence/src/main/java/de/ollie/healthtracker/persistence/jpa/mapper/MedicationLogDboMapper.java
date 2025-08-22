package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationLogDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface MedicationLogDboMapper {
	MedicationLog toModel(MedicationLogDbo dbo);

	MedicationLogDbo toDbo(MedicationLog model);
}
