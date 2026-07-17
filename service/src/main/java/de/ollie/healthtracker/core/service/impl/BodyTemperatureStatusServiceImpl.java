package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.BodyTemperatureStatusService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureStatus;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import jakarta.inject.Named;
import java.math.BigDecimal;

@Named
public class BodyTemperatureStatusServiceImpl implements BodyTemperatureStatusService {

	static final double DEFAULT_MIN_CELSIUS = 36.5;
	static final double DEFAULT_MAX_CELSIUS = 37.4;

	@Override
	public BodyTemperatureStatus calculateStatus(BigDecimal celsius, PointOfMeasurement pointOfMeasurement) {
		double value = celsius.doubleValue();
		double min = pointOfMeasurement != null && pointOfMeasurement.getRegularMinCelsius() != null
			? pointOfMeasurement.getRegularMinCelsius().doubleValue()
			: DEFAULT_MIN_CELSIUS;
		double max = pointOfMeasurement != null && pointOfMeasurement.getRegularMaxCelsius() != null
			? pointOfMeasurement.getRegularMaxCelsius().doubleValue()
			: DEFAULT_MAX_CELSIUS;
		if (value < min) {
			return BodyTemperatureStatus.BELOW;
		}
		if (value > max) {
			return BodyTemperatureStatus.ABOVE;
		}
		return BodyTemperatureStatus.NORMAL;
	}
}
