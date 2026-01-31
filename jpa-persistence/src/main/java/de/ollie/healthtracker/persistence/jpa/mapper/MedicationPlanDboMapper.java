package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationPlanDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface MedicationPlanDboMapper {
	MedicationPlan toModel(MedicationPlanDbo dbo);

	MedicationPlanDbo toDbo(MedicationPlan model);
}
