package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.port.persistence.MedicationPlanPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.MedicationPlanDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationPlanDboRepository;
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
class MedicationPlanPersistenceJpaAdapter implements MedicationPlanPersistencePort {

	private final DboFactory dboFactory;
	private final MedicationPlanDboMapper mapper;
	private final MedicationPlanDboRepository repository;

	@Override
	public MedicationPlan create(
		LocalDate endDate,
		Medication medication,
		MedicationUnit medicationUnit,
		LocalDate nextDateOfIntake,
		boolean selfMedication,
		LocalDate startDate,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	) {
		return mapper.toModel(
			repository.save(
				dboFactory.createMedicationPlan(
					endDate,
					medication.getId(),
					medicationUnit.getId(),
					nextDateOfIntake,
					selfMedication,
					startDate,
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
	public Optional<MedicationPlan> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public List<MedicationPlan> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public MedicationPlan update(MedicationPlan toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
