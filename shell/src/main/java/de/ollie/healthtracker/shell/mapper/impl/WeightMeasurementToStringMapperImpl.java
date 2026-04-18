package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.shell.mapper.WeightMeasurementToStringMapper;
import jakarta.inject.Named;

@Named
class WeightMeasurementToStringMapperImpl implements WeightMeasurementToStringMapper {

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
	public String map(WeightMeasurement model) {
		return model == null
			? null
			: String.format(LINE_FORMAT,
				model.getId(),
				model.toString()
			);
	}

}