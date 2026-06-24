package de.ollie.healthtracker.gui.swing.chart;

import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;

/**
 * A single data point for the {@link BloodPressureChartJComponent}: the (averaged) blood pressure values of one day of
 * the month plus the resulting WHO classification. The {@code day} (1-31) is plotted on the x-axis, the mmHg / pulse
 * values on the y-axis, and {@code status} drives the color of the status bar at the top of the chart.
 */
public record BloodPressureChartData(
	int day,
	int systolic,
	int diastolic,
	int pulse,
	WhoBloodPressureClassification status
) {}
