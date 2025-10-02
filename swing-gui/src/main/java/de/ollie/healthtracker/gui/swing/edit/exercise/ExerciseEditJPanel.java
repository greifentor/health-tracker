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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import lombok.Generated;

/**
 * HANDMADE CODE
 */
@Generated
public class ExerciseEditJPanel extends AbstractEditPanel<Exercise> {

	public static final String BODY_PART_ITEM_PROVIDER_ID = "body-part-item-provider";

	private JTextField textFieldName;
	private JComboBox<BodyPart> comboBoxBodyPart;
	private JTextArea textAreaDescription;

	public ExerciseEditJPanel(Exercise toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		p.add(createLabelSubPanel("Name:", "Body Part:"));
		p.add(createLabelSubPanel("Desciption:"));
		return p;
	}

	@Override
	protected JPanel createComponentPanel(Exercise toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		JPanel upper = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		upper.add(textFieldName);
		List<BodyPart> listBodyPart = ((ItemProvider<BodyPart>) itemProviders.get(BODY_PART_ITEM_PROVIDER_ID)).getItem();
		comboBoxBodyPart = new JComboBox<>(listBodyPart.toArray(new BodyPart[listBodyPart.size()]));
		comboBoxBodyPart.setSelectedItem(toEdit.getBodyPart());
		comboBoxBodyPart.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		upper.add(comboBoxBodyPart);
		JPanel lower = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		textAreaDescription = new JTextArea(toEdit.getDescription(), 5, 40);
		lower.add(new JScrollPane(textAreaDescription));
		p.add(upper);
		p.add(lower);
		return p;
	}

	@Override
	public Exercise getCurrentContent() {
		return new Exercise()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setBodyPart(((BodyPart) comboBoxBodyPart.getSelectedItem()))
			.setDescription(textAreaDescription.getText());
	}
}
