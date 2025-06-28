package de.ollie.healthtracker.shell.handler.impl;

import de.ollie.healthtracker.shell.handler.OutputHandler;
import jakarta.inject.Named;

@Named
public class SystemOutOutputHandler implements OutputHandler {

	@Override
	public void println(String s) {
		System.out.println(s);
	}
}
