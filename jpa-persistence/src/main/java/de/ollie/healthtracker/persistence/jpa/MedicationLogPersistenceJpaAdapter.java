package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.port.persistence.MedicationLogPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.MedicationLogDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationLogDboRepository;
import jakarta.inject.Named;
import java.math.BigDecimal;
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
class MedicationLogPersistenceJpaAdapter implements MedicationLogPersistencePort {

	private final DboFactory dboFactory;
	private final MedicationLogDboMapper mapper;
	private final MedicationLogDboRepository repository;

	@Override
	public MedicationLog create(
		Medication medication,
		MedicationUnit medicationUnit,
		LocalDate dateOfIntake,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	) {
		return mapper.toModel(
			repository.save(
				dboFactory.createMedicationLog(
					medication.getId(),
					medicationUnit.getId(),
					dateOfIntake,
					timeOfIntake,
					unitCount
				)
			)
		);
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<MedicationLog> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public List<MedicationLog> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public MedicationLog update(MedicationLog toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
