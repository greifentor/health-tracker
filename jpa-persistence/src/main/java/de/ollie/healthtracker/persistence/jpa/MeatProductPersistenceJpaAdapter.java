package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.core.service.model.MeatType;
import de.ollie.healthtracker.core.service.port.persistence.MeatProductPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatProductDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.MeatProductDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MeatProductDboRepository;
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
class MeatProductPersistenceJpaAdapter implements MeatProductPersistencePort {

	private final DboFactory dboFactory;
	private final MeatProductDboMapper mapper;
	private final MeatProductDboRepository repository;

	@Override
	public MeatProduct create(int amountInGr, String description, MeatType meatType) {
		return mapper.toModel(repository.save(dboFactory.createMeatProduct(amountInGr, description, meatType.getId())));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<MeatProduct> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<MeatProduct> findByIdOrDescriptionParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<MeatProductDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<MeatProductDbo> found = repository.findAllByDescriptionMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<MeatProduct> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public MeatProduct update(MeatProduct toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
