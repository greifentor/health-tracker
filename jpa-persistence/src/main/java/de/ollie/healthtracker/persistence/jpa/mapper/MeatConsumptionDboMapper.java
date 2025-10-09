package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatConsumptionDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface MeatConsumptionDboMapper {
	MeatConsumption toModel(MeatConsumptionDbo dbo);

	MeatConsumptionDbo toDbo(MeatConsumption model);
}
