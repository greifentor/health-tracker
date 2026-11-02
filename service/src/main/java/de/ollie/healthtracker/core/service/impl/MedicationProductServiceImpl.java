package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MedicationProductService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.port.persistence.MedicationProductPersistencePort;
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
class MedicationProductServiceImpl implements MedicationProductService {

	private final MedicationProductPersistencePort medicationProductPersistencePort;

	@Override
	public MedicationProduct createMedicationProduct(
		LocalDate expirationDate,
		Medication medication,
		BigDecimal packageSize,
		MedicationUnit medicationUnit
	) {
		return medicationProductPersistencePort.create(expirationDate, medication, packageSize, medicationUnit);
	}

	@Override
	public void deleteMedicationProduct(UUID id) {
		medicationProductPersistencePort.deleteById(id);
	}

	@Override
	public Optional<MedicationProduct> findById(UUID id) {
		return medicationProductPersistencePort.findById(id);
	}

	@Override
	public List<MedicationProduct> listMedicationProducts() {
		return medicationProductPersistencePort.list();
	}

	@Override
	public MedicationProduct updateMedicationProduct(MedicationProduct toSave) {
		return medicationProductPersistencePort.update(toSave);
	}
}
