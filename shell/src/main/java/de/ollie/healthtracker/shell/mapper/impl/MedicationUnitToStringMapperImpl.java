package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.shell.mapper.MedicationUnitToStringMapper;
import jakarta.inject.Named;

@Named
class MedicationUnitToStringMapperImpl implements MedicationUnitToStringMapper {

	private static final String LINE_FORMAT = "(%36s) %-30s %s";

	@Override
	public String getHeadLine() {
		return "(ID)                                   Name                           Token";
	}

	@Override
	public String getUnderLine() {
		return "--------------------------------------------------------------------------------------------------";
	}

	@Override
	public String map(MedicationUnit model) {
		return model == null ? null : String.format(LINE_FORMAT, model.getId(), model.getName(), model.getToken());
	}
}
