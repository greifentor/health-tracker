package de.ollie.healthtracker.gui.swing.edit.symptom;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.time.LocalDate;
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
public class SymptomEditJPanel extends AbstractEditPanel<Symptom> {

	public static final String BODY_PART_ITEM_PROVIDER_ID = "body-part-item-provider";

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldDescription;
	private JComboBox<BodyPart> comboBoxBodyPart;

	public SymptomEditJPanel(Symptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:");
	}

	@Override
	protected JPanel createComponentPanel(Symptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		textFieldDescription = new JTextField(toEdit.getDescription(), 40);
		p.add(textFieldDescription);
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
		return p;
	}

	@Override
	public Symptom getCurrentContent() {
		return new Symptom()
			.setId(toEdit.getId())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setDescription(textFieldDescription.getText())
			.setBodyPart(((BodyPart) comboBoxBodyPart.getSelectedItem()));
	}
}
