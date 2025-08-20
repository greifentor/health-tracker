package de.ollie.healthtracker.gui.swing.impl;

import de.ollie.healthtracker.gui.swing.ComponentFactory;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.BaseEditInternalFrame;
import de.ollie.healthtracker.gui.swing.edit.BaseEditInternalFrame.Observer;
import jakarta.inject.Named;
import javax.swing.JButton;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class EditDialogComponentFactoryImpl implements EditDialogComponentFactory {

	private final ComponentFactory componentFactory;

	@Override
	public <T> JButton createSaveButton(Observer<T> observer, BaseEditInternalFrame<T> internalFrame) {
		JButton b = componentFactory.createButton("Save");
		if (observer != null) {
			b.addActionListener(e -> {
				observer.onSave(internalFrame.getCurrentContent());
				internalFrame.closeDialog();
			});
		}
		return b;
	}
}
