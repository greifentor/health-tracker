package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholProductPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholProductDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.AlcoholProductDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.AlcoholProductDboRepository;
import jakarta.inject.Named;
import java.math.BigDecimal;
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
class AlcoholProductPersistenceJpaAdapter implements AlcoholProductPersistencePort {

	private final DboFactory dboFactory;
	private final AlcoholProductDboMapper mapper;
	private final AlcoholProductDboRepository repository;

	@Override
	public AlcoholProduct create(String name, BigDecimal percentVol, BigDecimal liter) {
		return mapper.toModel(repository.save(dboFactory.createAlcoholProduct(name, percentVol, liter)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<AlcoholProduct> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<AlcoholProduct> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<AlcoholProductDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<AlcoholProductDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<AlcoholProduct> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public AlcoholProduct update(AlcoholProduct toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
