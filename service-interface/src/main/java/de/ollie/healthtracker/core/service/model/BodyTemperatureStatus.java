package de.ollie.healthtracker.core.service.model;

/**
 * Status of a measured body temperature relative to the regular range of its point of measurement (or a default range
 * when none is assigned): {@link #BELOW} the minimum, {@link #NORMAL} within the range, or {@link #ABOVE} the maximum.
 */
public enum BodyTemperatureStatus {
	BELOW,
	NORMAL,
	ABOVE,
}
