package de.ollie.bptracker.core.service.model;

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
}
