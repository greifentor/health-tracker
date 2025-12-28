package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.shell.mapper.SymptomToStringMapper;
import jakarta.inject.Named;

@Named
class SymptomToStringMapperImpl implements SymptomToStringMapper {

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
	public String map(Symptom model) {
		return model == null
			? null
			: String.format(LINE_FORMAT,
				model.getId(),
				model.toString()
			);
	}

}