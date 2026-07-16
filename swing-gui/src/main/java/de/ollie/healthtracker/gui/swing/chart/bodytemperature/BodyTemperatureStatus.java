package de.ollie.healthtracker.gui.swing.chart.bodytemperature;

/**
 * Status of a measured body temperature relative to the regular range of its point of measurement (or the default range
 * 36.5-37.4 °C when no point of measurement is assigned): {@link #BELOW} the minimum, {@link #NORMAL} within the range,
 * or {@link #ABOVE} the maximum. Drives the color of the status bar in the {@link BodyTemperatureChartJComponent}.
 */
public enum BodyTemperatureStatus {
	BELOW,
	NORMAL,
	ABOVE,
}
