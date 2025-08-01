package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.core.service.port.persistence.SymptomPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.SymptomDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.SymptomDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.SymptomDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
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
class SymptomPersistenceJpaAdapter implements SymptomPersistencePort {

	private final DboFactory dboFactory;
	private final SymptomDboMapper mapper;
	private final SymptomDboRepository repository;

	@Override
	public Symptom create(String description, LocalDate dateOfRecording, LocalTime timeOfRecording) {
		return mapper.toModel(repository.save(dboFactory.createSymptom(description, dateOfRecording, timeOfRecording)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<Symptom> findByIdOrDescriptionParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<SymptomDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<SymptomDbo> found = repository.findAllByDescriptionMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<Symptom> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
