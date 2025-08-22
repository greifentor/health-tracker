package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface DoctorTypeDboMapper {
	DoctorType toModel(DoctorTypeDbo dbo);
	DoctorTypeDbo toDbo(DoctorType model);
}
