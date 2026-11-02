package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationProductDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface MedicationProductDboMapper {
	MedicationProduct toModel(MedicationProductDbo dbo);

	MedicationProductDbo toDbo(MedicationProduct model);
}
