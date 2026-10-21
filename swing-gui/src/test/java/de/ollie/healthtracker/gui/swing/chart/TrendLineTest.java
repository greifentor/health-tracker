package de.ollie.healthtracker.gui.swing.chart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class TrendLineTest {

	@Test
	void fitsThePositiveSlopeOfARisingLine() {
		// Run
		Optional<TrendLine> result = TrendLine.fit(new double[] { 0, 1, 2 }, new double[] { 0, 2, 4 });
		// Check
		assertEquals(2.0, result.orElseThrow().slope(), 1e-9);
	}

	@Test
	void fitsTheInterceptOfALine() {
		// Run
		Optional<TrendLine> result = TrendLine.fit(new double[] { 0, 1, 2 }, new double[] { 1, 3, 5 });
		// Check
		assertEquals(1.0, result.orElseThrow().intercept(), 1e-9);
	}

	@Test
	void valueAtReturnsThePointOnTheLine() {
		// Run
		double value = new TrendLine(2.0, 1.0).valueAt(3.0);
		// Check
		assertEquals(7.0, value, 1e-9);
	}

	@Test
	void returnsEmptyForFewerThanTwoPoints() {
		// Run
		Optional<TrendLine> result = TrendLine.fit(new double[] { 1 }, new double[] { 1 });
		// Check
		assertTrue(result.isEmpty());
	}

	@Test
	void returnsEmptyWhenAllXValuesAreEqual() {
		// Run
		Optional<TrendLine> result = TrendLine.fit(new double[] { 2, 2, 2 }, new double[] { 1, 2, 3 });
		// Check
		assertTrue(result.isEmpty());
	}
}
