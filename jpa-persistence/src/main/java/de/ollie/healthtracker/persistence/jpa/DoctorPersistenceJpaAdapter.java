package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.core.service.port.persistence.DoctorPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.DoctorDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorDboRepository;
import jakarta.inject.Named;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DoctorPersistenceJpaAdapter implements DoctorPersistencePort {

	private final DboFactory dboFactory;
	private final DoctorDboMapper mapper;
	private final DoctorDboRepository repository;

	@Override
	public Doctor create(String name, DoctorType doctorType) {
		DoctorDbo dbo = dboFactory.createDoctor(name, doctorType.getId());
		System.out.println("\n\n ---- " + dbo);
		return mapper.toModel(repository.save(dbo));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public List<Doctor> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
