package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.shell.mapper.BloodPressureMeasurementToStringMapper;
import jakarta.inject.Named;

@Named
class BloodPressureMeasurementToStringMapperImpl implements BloodPressureMeasurementToStringMapper {

	private static final String LINE_FORMAT = "%10s %5s %3d %3d %3d %-6s (%s)";

	@Override
	public String getHeadLine() {
		return "Date       Time  SYS DIA  PP Status (ID)";
	}

	@Override
	public String getUnderLine() {
		return "--------------------------------------------------------------------------";
	}

	@Override
	public String map(BloodPressureMeasurement bloodPressureMeasurement) {
		return bloodPressureMeasurement == null
			? null
			: String.format(
				LINE_FORMAT,
				dateToString(bloodPressureMeasurement.getDateOfRecording()),
				timeToString(bloodPressureMeasurement.getTimeOfRecording()),
				bloodPressureMeasurement.getSysMmHg(),
				bloodPressureMeasurement.getDiaMmHg(),
				bloodPressureMeasurement.getPulsePerMinute(),
				statusToString(bloodPressureMeasurement.getStatus()),
				bloodPressureMeasurement.getId()
			);
	}

	private String statusToString(BloodPressureMeasurementStatus status) {
		return status == null ? "-" : status.name();
	}
}
