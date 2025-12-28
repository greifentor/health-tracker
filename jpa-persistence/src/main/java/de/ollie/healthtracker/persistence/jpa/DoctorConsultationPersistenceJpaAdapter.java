package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.core.service.port.persistence.DoctorConsultationPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.DoctorConsultationDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorConsultationDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DoctorConsultationPersistenceJpaAdapter implements DoctorConsultationPersistencePort {

	private final DboFactory dboFactory;
	private final DoctorConsultationDboMapper mapper;
	private final DoctorConsultationDboRepository repository;

	@Override
	public DoctorConsultation create(
		LocalDate date,
		LocalTime time,
		Doctor doctor,
		boolean open,
		String reason,
		String result,
		DoctorConsultation subsequentAppointmentOf
	) {
		return mapper.toModel(
			repository.save(
				dboFactory.createDoctorConsultation(
					date,
					time,
					doctor.getId(),
					open,
					reason,
					result,
					subsequentAppointmentOf != null ? subsequentAppointmentOf.getId() : null
				)
			)
		);
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<DoctorConsultation> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public List<DoctorConsultation> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public DoctorConsultation update(DoctorConsultation toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
