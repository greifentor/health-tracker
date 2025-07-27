package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.shell.mapper.DoctorTypeToStringMapper;
import jakarta.inject.Named;

@Named
class DoctorTypeToStringMapperImpl implements DoctorTypeToStringMapper {

	private static final String LINE_FORMAT = "(%36s) %s";

	@Override
	public String getHeadLine() {
		return "(ID)                                   Name";
	}

	@Override
	public String getUnderLine() {
		return "-----------------------------------------------------------------------------------";
	}

	@Override
	public String map(DoctorType model) {
		return model == null
			? null
			: String.format(LINE_FORMAT, model.getId(), (model.getName() == null ? "-" : model.getName()));
	}
}
