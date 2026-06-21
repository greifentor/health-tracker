package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholConsumptionPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.AlcoholConsumptionDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.AlcoholConsumptionDboRepository;
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
class AlcoholConsumptionPersistenceJpaAdapter implements AlcoholConsumptionPersistencePort {

	private final DboFactory dboFactory;
	private final AlcoholConsumptionDboMapper mapper;
	private final AlcoholConsumptionDboRepository repository;

	@Override
	public AlcoholConsumption create(LocalDate date, AlcoholProduct alcoholProduct, String comment) {
		return mapper.toModel(repository.save(dboFactory.createAlcoholConsumption(date, alcoholProduct.getId(), comment)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<AlcoholConsumption> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public List<AlcoholConsumption> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public AlcoholConsumption update(AlcoholConsumption toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
