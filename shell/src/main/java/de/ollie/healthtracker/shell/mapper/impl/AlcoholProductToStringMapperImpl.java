package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.shell.mapper.AlcoholProductToStringMapper;
import jakarta.inject.Named;

@Named
class AlcoholProductToStringMapperImpl implements AlcoholProductToStringMapper {

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
	public String map(AlcoholProduct model) {
		return model == null
			? null
			: String.format(LINE_FORMAT,
				model.getId(),
				model.toString()
			);
	}

}