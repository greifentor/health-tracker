package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.port.print.PrintPort.Details;
import de.ollie.healthtracker.shell.mapper.DetailsToStringMapper;
import jakarta.inject.Named;

@Named
class DetailsToStringMapperImpl implements DetailsToStringMapper {

	private static final String LINE_FORMAT = "%-20s %s";

	@Override
	public String getHeadLine() {
		return "Id                   Description";
	}

	@Override
	public String getUnderLine() {
		return "----------------------------------------------------------------------------------------------";
	}

	@Override
	public String map(Details details) {
		return details == null ? null : String.format(LINE_FORMAT, details.getId(), details.getDescription());
	}
}
