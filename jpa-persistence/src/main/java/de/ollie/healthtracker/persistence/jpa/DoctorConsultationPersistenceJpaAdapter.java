package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.core.service.port.persistence.DoctorConsultationPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.DoctorConsultationDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorConsultationDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
@RequiredArgsConstructor
class DoctorConsultationPersistenceJpaAdapter implements DoctorConsultationPersistencePort {

	private final DboFactory dboFactory;
	private final DoctorConsultationDboMapper mapper;
	private final DoctorConsultationDboRepository repository;

	@Override
	public DoctorConsultation create(LocalDate date, LocalTime time, Doctor doctor, String reason, String result) {
		return mapper.toModel(
			repository.save(dboFactory.createDoctorConsultation(date, time, doctor.getId(), reason, result))
		);
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public List<DoctorConsultation> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
