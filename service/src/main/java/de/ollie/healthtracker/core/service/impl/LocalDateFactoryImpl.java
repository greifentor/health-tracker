package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.LocalDateFactory;
import jakarta.inject.Named;
import java.time.LocalDate;

@Named
public class LocalDateFactoryImpl implements LocalDateFactory {

	@Override
	public LocalDate now() {
		return LocalDate.now();
	}
}
