package de.ollie.healthtracker.gui.swing.edit.exercise;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Exercise;
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
public class ExerciseEditJPanel extends AbstractEditPanel<Exercise> {

	public static final String BODY_PART_ITEM_PROVIDER_ID = "body-part-item-provider";

	private JTextField textFieldName;
	private JComboBox<BodyPart> comboBoxBodyPart;
	private JTextField textFieldDescription;

	public ExerciseEditJPanel(Exercise toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:", "Body Part:", "Description:");
	}

	@Override
	protected JPanel createComponentPanel(Exercise toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		List<BodyPart> listBodyPart = ((ItemProvider<BodyPart>) itemProviders.get(BODY_PART_ITEM_PROVIDER_ID)).getItem();
		comboBoxBodyPart = new JComboBox<>(listBodyPart.toArray(new BodyPart[listBodyPart.size()]));
		comboBoxBodyPart.setSelectedItem(toEdit.getBodyPart());
		comboBoxBodyPart.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxBodyPart);
		textFieldDescription = new JTextField(toEdit.getDescription(), 40);
		p.add(textFieldDescription);
		return p;
	}

	@Override
	public Exercise getCurrentContent() {
		return new Exercise()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setBodyPart(((BodyPart) comboBoxBodyPart.getSelectedItem()))
			.setDescription(textFieldDescription.getText());
	}
}
