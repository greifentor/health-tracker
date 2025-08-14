package de.ollie.healthtracker.gui.swing.impl;

import de.ollie.healthtracker.gui.swing.ComponentFactory;
import jakarta.inject.Named;
import javax.swing.JButton;

@Named
class ComponentFactoryImpl implements ComponentFactory {

	@Override
	public JButton createButton(String label) {
		return new JButton(label);
	}
}
