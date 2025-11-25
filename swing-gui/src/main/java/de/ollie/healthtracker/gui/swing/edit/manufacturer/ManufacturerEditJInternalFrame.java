package de.ollie.healthtracker.gui.swing.edit.manufacturer;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
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
public class ManufacturerEditJInternalFrame extends AbstractEditJInternalFrame<Manufacturer> {

	public ManufacturerEditJInternalFrame(
		Manufacturer toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<Manufacturer> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Manufacturer", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(Manufacturer toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new ManufacturerEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
