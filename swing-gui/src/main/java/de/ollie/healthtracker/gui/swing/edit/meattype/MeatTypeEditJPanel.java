package de.ollie.healthtracker.gui.swing.edit.meattype;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.MeatType;
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
public class MeatTypeEditJPanel extends AbstractEditPanel<MeatType> {

	private JTextField textFieldName;
	private JComboBox<MeatCategory> comboBoxCategory;

	public MeatTypeEditJPanel(MeatType toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:", "Category:");
	}

	@Override
	protected JPanel createComponentPanel(MeatType toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		comboBoxCategory = new JComboBox<>(MeatCategory.values());
		comboBoxCategory.setSelectedItem(toEdit.getCategory());
		p.add(comboBoxCategory);
		return p;
	}

	@Override
	public MeatType getCurrentContent() {
		return new MeatType()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setCategory(((MeatCategory) comboBoxCategory.getSelectedItem()));
	}
}
