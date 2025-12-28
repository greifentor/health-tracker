package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.Exercise;
import de.ollie.healthtracker.shell.mapper.ExerciseToStringMapper;
import jakarta.inject.Named;

@Named
class ExerciseToStringMapperImpl implements ExerciseToStringMapper {

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
	public String map(Exercise model) {
		return model == null
			? null
			: String.format(LINE_FORMAT,
				model.getId(),
				model.toString()
			);
	}

}