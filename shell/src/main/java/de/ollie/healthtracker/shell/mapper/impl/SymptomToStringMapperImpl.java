package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.shell.mapper.SymptomToStringMapper;
import jakarta.inject.Named;

@Named
class SymptomToStringMapperImpl implements SymptomToStringMapper {

	private static final String LINE_FORMAT = "%10s (%36s) %s";

	@Override
	public String getHeadLine() {
		return "Date       (ID)                                   Description";
	}

	@Override
	public String getUnderLine() {
		return "--------------------------------------------------------------------------------------------";
	}

	@Override
	public String map(Symptom model) {
		return model == null
			? null
			: String.format(
				LINE_FORMAT,
				dateToString(model.getDateOfRecording()),
				model.getId(),
				(model.getDescription() == null ? "-" : model.getDescription())
			);
	}
}
