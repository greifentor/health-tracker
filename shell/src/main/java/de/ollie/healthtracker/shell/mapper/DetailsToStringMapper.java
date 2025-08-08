package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.port.print.PrintPort.Details;

public interface DetailsToStringMapper extends ToStringMapper {
	String getHeadLine();

	String getUnderLine();

	String map(Details model);
}
