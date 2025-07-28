package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.shell.mapper.ManufacturerToStringMapper;
import jakarta.inject.Named;

@Named
class ManufacturerToStringMapperImpl implements ManufacturerToStringMapper {

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
	public String map(Manufacturer model) {
		return model == null ? null : String.format(LINE_FORMAT, model.getId(), model.getName());
	}
}
