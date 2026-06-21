package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholConsumptionDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface AlcoholConsumptionDboMapper {
	AlcoholConsumption toModel(AlcoholConsumptionDbo dbo);

	AlcoholConsumptionDbo toDbo(AlcoholConsumption model);
}
