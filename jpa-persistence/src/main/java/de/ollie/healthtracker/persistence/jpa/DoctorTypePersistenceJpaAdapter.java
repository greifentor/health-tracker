package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.core.service.port.persistence.DoctorTypePersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.DoctorTypeDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorTypeDboRepository;
import jakarta.inject.Named;
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
class DoctorTypePersistenceJpaAdapter implements DoctorTypePersistencePort {

	private final DboFactory dboFactory;
	private final DoctorTypeDboMapper mapper;
	private final DoctorTypeDboRepository repository;

	@Override
	public DoctorType create(String name) {
		return mapper.toModel(repository.save(dboFactory.createDoctorType(name)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public List<DoctorType> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
