package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorConsultationDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface DoctorConsultationDboMapper {
	DoctorConsultation toModel(DoctorConsultationDbo dbo);
	DoctorConsultationDbo toDbo(DoctorConsultation model);
}
