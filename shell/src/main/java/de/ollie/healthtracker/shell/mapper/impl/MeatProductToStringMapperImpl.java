package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.shell.mapper.MeatProductToStringMapper;
import jakarta.inject.Named;

@Named
class MeatProductToStringMapperImpl implements MeatProductToStringMapper {

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
	public String map(MeatProduct model) {
		return model == null
			? null
			: String.format(LINE_FORMAT,
				model.getId(),
				model.toString()
			);
	}

}