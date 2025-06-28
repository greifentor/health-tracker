package de.ollie.healthtracker.core.service.model;

import lombok.Getter;

public enum BloodPressureMeasurementStatus {
	GREEN(1),
	YELLOW(2),
	ORANGE(3),
	RED(4);

	@Getter
	private int value;

	private BloodPressureMeasurementStatus(int value) {
		this.value = value;
	}

	public static BloodPressureMeasurementStatus ofValue(int value) {
		for (BloodPressureMeasurementStatus status : BloodPressureMeasurementStatus.values()) {
			if (status.getValue() == value) {
				return status;
			}
		}
		return null;
	}
}
