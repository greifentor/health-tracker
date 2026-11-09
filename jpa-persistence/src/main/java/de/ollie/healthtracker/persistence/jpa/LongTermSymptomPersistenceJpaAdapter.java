package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.LongTermSymptom;
import de.ollie.healthtracker.core.service.port.persistence.LongTermSymptomPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.LongTermSymptomDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.LongTermSymptomDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.LongTermSymptomDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
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
class LongTermSymptomPersistenceJpaAdapter implements LongTermSymptomPersistencePort {

	private final DboFactory dboFactory;
	private final LongTermSymptomDboMapper mapper;
	private final LongTermSymptomDboRepository repository;

	@Override
	public LongTermSymptom create(
		LocalDate startDate,
		LocalDate endDate,
		BodyPart bodyPart,
		String description,
		boolean unverified
	) {
		return mapper.toModel(
			repository.save(dboFactory.createLongTermSymptom(startDate, endDate, bodyPart.getId(), description, unverified))
		);
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<LongTermSymptom> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<LongTermSymptom> findByIdOrDescriptionParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<LongTermSymptomDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<LongTermSymptomDbo> found = repository.findAllByDescriptionMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException(
						"More than one element found for description containing: " + nameParticleOrId
					);
				})
			)
		);
	}

	@Override
	public List<LongTermSymptom> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public LongTermSymptom update(LongTermSymptom toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
