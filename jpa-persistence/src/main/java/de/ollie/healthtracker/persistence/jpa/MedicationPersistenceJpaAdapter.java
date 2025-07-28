package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.port.persistence.MedicationPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.MedicationDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationDboRepository;
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
class MedicationPersistenceJpaAdapter implements MedicationPersistencePort {

	private final DboFactory dboFactory;
	private final MedicationDboMapper mapper;
	private final MedicationDboRepository repository;

	@Override
	public Medication create(String name, Manufacturer manufacturer) {
		return mapper.toModel(repository.save(dboFactory.createMedication(name, manufacturer.getId())));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<Medication> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<MedicationDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<MedicationDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<Medication> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
