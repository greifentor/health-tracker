package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.port.persistence.MedicationUnitPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationUnitDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.MedicationUnitDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationUnitDboRepository;
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
class MedicationUnitPersistenceJpaAdapter implements MedicationUnitPersistencePort {

	private final DboFactory dboFactory;
	private final MedicationUnitDboMapper mapper;
	private final MedicationUnitDboRepository repository;

	@Override
	public MedicationUnit create(String name, String token) {
		return mapper.toModel(repository.save(dboFactory.createMedicationUnit(name, token)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<MedicationUnit> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<MedicationUnit> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<MedicationUnitDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<MedicationUnitDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<MedicationUnit> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public MedicationUnit update(MedicationUnit toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
