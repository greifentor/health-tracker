package de.ollie.healthtracker.gui.swing.chart.bodytemperature;

import de.ollie.healthtracker.core.service.model.BodyTemperatureStatus;

/**
 * A single data point for the {@link BodyTemperatureChartJComponent}: the (averaged) body temperature of one day of the
 * month plus the resulting {@link BodyTemperatureStatus}. The {@code day} (1-31) is plotted on the x-axis, the
 * {@code celsius} value on the (configurable) y-axis, and {@code status} drives the color of the status bar at the top
 * of the chart. {@code minCelsius} and {@code maxCelsius} are that day's regular range derived from the points of
 * measurement used (the minimum of the minima and the maximum of the maxima); either is {@code null} when no range is
 * known for the day.
 */
public record BodyTemperatureChartData(
	int day,
	double celsius,
	BodyTemperatureStatus status,
	Double minCelsius,
	Double maxCelsius
) {}
