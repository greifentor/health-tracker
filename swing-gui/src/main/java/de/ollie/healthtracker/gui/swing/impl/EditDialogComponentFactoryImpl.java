package de.ollie.healthtracker.gui.swing.impl;

import de.ollie.healthtracker.gui.swing.BaseEditDialog;
import de.ollie.healthtracker.gui.swing.BaseEditDialog.Observer;
import de.ollie.healthtracker.gui.swing.ComponentFactory;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import jakarta.inject.Named;
import javax.swing.JButton;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class EditDialogComponentFactoryImpl implements EditDialogComponentFactory {

	private final ComponentFactory componentFactory;

	@Override
	public <T> JButton createSaveButton(Observer<T> observer, BaseEditDialog<T> dialog) {
		JButton b = componentFactory.createButton("Save");
		if (observer != null) {
			b.addActionListener(e -> {
				observer.onSave(dialog.getToEdit());
				dialog.closeDialog();
			});
		}
		return b;
	}
}
