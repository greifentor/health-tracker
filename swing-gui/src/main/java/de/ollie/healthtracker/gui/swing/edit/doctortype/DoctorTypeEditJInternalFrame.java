package de.ollie.healthtracker.gui.swing.edit.doctortype;

import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class DoctorTypeEditJInternalFrame extends AbstractEditJInternalFrame<DoctorType> {

	public DoctorTypeEditJInternalFrame(
		DoctorType toEdit,
		String className,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<DoctorType> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, className, toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(DoctorType toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new DoctorTypeEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
