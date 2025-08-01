package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.shell.mapper.MedicationLogToStringMapper;
import jakarta.inject.Named;
import java.math.BigDecimal;

@Named
class MedicationLogToStringMapperImpl implements MedicationLogToStringMapper {

	private static final String LINE_FORMAT = "(%36s) %-10s %-5s %-30s %-10s %2.2f";

	@Override
	public String getHeadLine() {
		return "(ID)                                   Date       Time  Medication                     Unit      Count";
	}

	@Override
	public String getUnderLine() {
		return "------------------------------------------------------------------------------------------------------";
	}

	@Override
	public String map(MedicationLog model) {
		return model == null
			? null
			: String.format(
				LINE_FORMAT,
				model.getId(),
				dateToString(model.getDateOfIntake()),
				timeToString(model.getTimeOfIntake()),
				getMedicationName(model),
				getMedicationUnitToken(model),
				getUnitCount(model)
			);
	}

	private String getMedicationName(MedicationLog model) {
		return model.getMedication() != null ? model.getMedication().getName() : "-";
	}

	private String getMedicationUnitToken(MedicationLog model) {
		return model.getMedicationUnit() != null ? model.getMedicationUnit().getToken() : "-";
	}

	private BigDecimal getUnitCount(MedicationLog model) {
		return model.getUnitCount() != null ? model.getUnitCount() : new BigDecimal(0);
	}
}
