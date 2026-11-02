package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.port.persistence.MedicationProductPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.MedicationProductDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationProductDboRepository;
import jakarta.inject.Named;
import java.math.BigDecimal;
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
class MedicationProductPersistenceJpaAdapter implements MedicationProductPersistencePort {

	private final DboFactory dboFactory;
	private final MedicationProductDboMapper mapper;
	private final MedicationProductDboRepository repository;

	@Override
	public MedicationProduct create(
		LocalDate expirationDate,
		Medication medication,
		BigDecimal packageSize,
		MedicationUnit medicationUnit
	) {
		return mapper.toModel(
			repository.save(
				dboFactory.createMedicationProduct(expirationDate, medication.getId(), packageSize, medicationUnit.getId())
			)
		);
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<MedicationProduct> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public List<MedicationProduct> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public MedicationProduct update(MedicationProduct toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
