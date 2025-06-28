package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.UuidFactory;
import jakarta.inject.Named;
import java.util.UUID;

@Named
class UuidFactoryImpl implements UuidFactory {

	@Override
	public UUID create() {
		return UUID.randomUUID();
	}
}
