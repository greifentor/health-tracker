package de.ollie.healthtracker.gui.swing.edit.bodypart;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
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
public class BodyPartEditJPanel extends AbstractEditPanel<BodyPart> {

	public static final String GENERAL_BODY_PART_ITEM_PROVIDER_ID = "general-body-part-item-provider";

	private JTextField textFieldName;
	private JComboBox<GeneralBodyPart> comboBoxGeneralBodyPart;

	public BodyPartEditJPanel(BodyPart toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:", "General Body Part:");
	}

	@Override
	protected JPanel createComponentPanel(BodyPart toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		List<GeneralBodyPart> listGeneralBodyPart =
			((ItemProvider<GeneralBodyPart>) itemProviders.get(GENERAL_BODY_PART_ITEM_PROVIDER_ID)).getItem();
		comboBoxGeneralBodyPart =
			new JComboBox<>(listGeneralBodyPart.toArray(new GeneralBodyPart[listGeneralBodyPart.size()]));
		comboBoxGeneralBodyPart.setSelectedItem(toEdit.getGeneralBodyPart());
		comboBoxGeneralBodyPart.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxGeneralBodyPart);
		return p;
	}

	@Override
	public BodyPart getCurrentContent() {
		return new BodyPart()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setGeneralBodyPart(((GeneralBodyPart) comboBoxGeneralBodyPart.getSelectedItem()));
	}
}
