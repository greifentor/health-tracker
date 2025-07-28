package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.shell.mapper.MedicationToStringMapper;
import jakarta.inject.Named;

@Named
class MedicationToStringMapperImpl implements MedicationToStringMapper {

	private static final String LINE_FORMAT = "(%36s) %-30s %s";

	@Override
	public String getHeadLine() {
		return "(ID)                                   Name                           Manufacturer";
	}

	@Override
	public String getUnderLine() {
		return "---------------------------------------------------------------------------------------------------";
	}

	@Override
	public String map(Medication model) {
		return model == null
			? null
			: String.format(
				LINE_FORMAT,
				model.getId(),
				model.getName(),
				(model.getManufacturer() != null ? model.getManufacturer().getName() : "-")
			);
	}
}
