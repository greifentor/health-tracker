package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface MedicationDboMapper {
	Medication toModel(MedicationDbo dbo);
}
