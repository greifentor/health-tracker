package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.MeatType;
import de.ollie.healthtracker.shell.mapper.MeatTypeToStringMapper;
import jakarta.inject.Named;

@Named
class MeatTypeToStringMapperImpl implements MeatTypeToStringMapper {

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
	public String map(MeatType model) {
		return model == null
			? null
			: String.format(LINE_FORMAT,
				model.getId(),
				model.toString()
			);
	}

}