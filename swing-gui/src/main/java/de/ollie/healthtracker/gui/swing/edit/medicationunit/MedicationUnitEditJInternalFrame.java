package de.ollie.healthtracker.gui.swing.edit.medicationunit;

import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.util.Map;
import java.util.UUID;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MedicationUnitEditJInternalFrame extends AbstractEditJInternalFrame<MedicationUnit> {

	public MedicationUnitEditJInternalFrame(
		MedicationUnit toEdit,
		String className,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<MedicationUnit> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, className, toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(MedicationUnit toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MedicationUnitEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
