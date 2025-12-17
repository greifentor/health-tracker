package de.ollie.healthtracker.gui.swing.external.viewer.pdf;

import jakarta.inject.Named;

@Named
class ProcessBuilderFactory {

	ProcessBuilder create(String command, String parameters) {
		return new ProcessBuilder(command, parameters);
	}
}
