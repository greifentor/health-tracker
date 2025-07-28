package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.port.persistence.ManufacturerPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.ManufacturerDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.ManufacturerDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.ManufacturerDboRepository;
import jakarta.inject.Named;
import java.util.List;
import java.util.Optional;
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
class ManufacturerPersistenceJpaAdapter implements ManufacturerPersistencePort {

	private final DboFactory dboFactory;
	private final ManufacturerDboMapper mapper;
	private final ManufacturerDboRepository repository;

	@Override
	public Manufacturer create(String name) {
		return mapper.toModel(repository.save(dboFactory.createManufacturer(name)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<Manufacturer> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<ManufacturerDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<ManufacturerDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<Manufacturer> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
