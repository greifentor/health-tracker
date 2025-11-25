package de.ollie.healthtracker.gui.swing.edit.medication;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MedicationEditJPanel extends AbstractEditPanel<Medication> {

	public static final String MANUFACTURER_ITEM_PROVIDER_ID = "manufacturer-item-provider";

	private JTextField textFieldName;
	private JComboBox<Manufacturer> comboBoxManufacturer;

	public MedicationEditJPanel(Medication toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:", "Manufacturer:");
	}

	@Override
	protected JPanel createComponentPanel(Medication toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		List<Manufacturer> listManufacturer =
			((ItemProvider<Manufacturer>) itemProviders.get(MANUFACTURER_ITEM_PROVIDER_ID)).getItem();
		comboBoxManufacturer = new JComboBox<>(listManufacturer.toArray(new Manufacturer[listManufacturer.size()]));
		comboBoxManufacturer.setSelectedItem(toEdit.getManufacturer());
		comboBoxManufacturer.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxManufacturer);
		return p;
	}

	@Override
	public Medication getCurrentContent() {
		return new Medication()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setManufacturer(((Manufacturer) comboBoxManufacturer.getSelectedItem()));
	}
}
