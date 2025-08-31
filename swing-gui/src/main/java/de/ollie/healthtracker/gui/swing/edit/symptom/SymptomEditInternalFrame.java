package de.ollie.healthtracker.gui.swing.edit.symptom;

import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class SymptomEditInternalFrame extends AbstractEditInternalFrame<Symptom> {

	public SymptomEditInternalFrame(
		Symptom toEdit,
		String className,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<Symptom> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, className, toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(Symptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new SymptomEditPanel(toEdit, itemProviders);
		return editPanel;
	}
}
