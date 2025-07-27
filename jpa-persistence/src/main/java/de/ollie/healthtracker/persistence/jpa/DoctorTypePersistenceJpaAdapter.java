package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.core.service.port.persistence.DoctorTypePersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.DoctorTypeDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorTypeDboRepository;
import jakarta.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

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
	public Optional<DoctorType> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<DoctorTypeDbo> doctorType = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			doctorType = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				doctorType.orElseGet(() -> {
					List<DoctorTypeDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<DoctorType> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
