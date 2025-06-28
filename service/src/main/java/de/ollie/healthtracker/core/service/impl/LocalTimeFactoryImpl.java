package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.LocalTimeFactory;
import jakarta.inject.Named;
import java.time.LocalTime;

@Named
public class LocalTimeFactoryImpl implements LocalTimeFactory {

	@Override
	public LocalTime now() {
		return LocalTime.now();
	}
}
