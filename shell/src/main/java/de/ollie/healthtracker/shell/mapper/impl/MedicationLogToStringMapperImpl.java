package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.shell.mapper.MedicationLogToStringMapper;
import jakarta.inject.Named;

@Named
class MedicationLogToStringMapperImpl implements MedicationLogToStringMapper {

	private static final String LINE_FORMAT = "(%36s) %s";

	@Override
	public String getHeadLine() {
		return "(ID)                                   Content";
	}

	@Override
	public String getUnderLine() {
		return "-----------------------------------------------------------------------------------";
	}

	@Override
	public String map(MedicationLog model) {
		return model == null
			? null
			: String.format(LINE_FORMAT,
				model.getId(),
				model.toString()
			);
	}

}