package de.ollie.bptracker.persistence.jpa.dbo;

import lombok.Getter;

public enum BloodPressureMeasurementStatusDbo {
	GREEN(1),
	YELLOW(2),
	ORANGE(3),
	RED(4);

	@Getter
	private int value;

	private BloodPressureMeasurementStatusDbo(int value) {
		this.value = value;
	}
}
