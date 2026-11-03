package de.ollie.healthtracker.gui.swing.chart.meatfishmass;

/**
 * A single data point for the {@link MeatFishMassChartJComponent}. The {@code year} and {@code month} (1-12) form the
 * x-axis label, {@code meatKilograms} and {@code fishKilograms} (the consumed mass of that month) are plotted on the
 * y-axis.
 */
public record MeatFishMassChartData(int year, int month, double meatKilograms, double fishKilograms) {}
