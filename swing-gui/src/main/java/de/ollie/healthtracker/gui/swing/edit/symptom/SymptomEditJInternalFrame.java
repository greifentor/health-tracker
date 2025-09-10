package de.ollie.healthtracker.gui.swing.edit.symptom;

import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class SymptomEditJInternalFrame extends AbstractEditJInternalFrame<Symptom> {

	public SymptomEditJInternalFrame(
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
		editPanel = new SymptomEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
