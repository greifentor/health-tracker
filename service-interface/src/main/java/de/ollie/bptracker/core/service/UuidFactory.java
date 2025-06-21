package de.ollie.bptracker.core.service;

import jakarta.inject.Named;
import java.util.UUID;

@Named
public class UuidFactory {

	public UUID create() {
		return UUID.randomUUID();
	}
}
