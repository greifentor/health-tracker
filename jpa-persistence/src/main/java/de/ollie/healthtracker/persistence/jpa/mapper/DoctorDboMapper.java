package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface DoctorDboMapper {
	DoctorDbo toDbo(Doctor model);

	Doctor toModel(DoctorDbo dbo);
}
