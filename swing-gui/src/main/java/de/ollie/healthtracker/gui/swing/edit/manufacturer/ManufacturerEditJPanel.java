package de.ollie.healthtracker.gui.swing.edit.manufacturer;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.util.Map;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class ManufacturerEditJPanel extends AbstractEditPanel<Manufacturer> {

	private JTextField textFieldName;

	public ManufacturerEditJPanel(Manufacturer toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:");
	}

	@Override
	protected JPanel createComponentPanel(Manufacturer toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		return p;
	}

	@Override
	public Manufacturer getCurrentContent() {
		return new Manufacturer().setId(toEdit.getId()).setName(textFieldName.getText());
	}
}
