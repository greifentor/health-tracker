package de.ollie.healthtracker.gui.swing.chart.nutrition;

import java.awt.Color;
import java.util.function.ToIntFunction;

/** One graph: its legend label, color and the value it reads from a {@link NutritionChartData}. */
record Series(String label, Color color, ToIntFunction<NutritionChartData> valueAccessor) {}
