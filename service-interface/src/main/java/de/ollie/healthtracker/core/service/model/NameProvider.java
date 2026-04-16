package de.ollie.healthtracker.core.service.model;

public interface NameProvider<T> {
	String getName();

	T setName(String newName);
}
