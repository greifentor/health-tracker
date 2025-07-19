package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorTypeDboMapper {
	DoctorType toModel(DoctorTypeDbo dbo);
}
