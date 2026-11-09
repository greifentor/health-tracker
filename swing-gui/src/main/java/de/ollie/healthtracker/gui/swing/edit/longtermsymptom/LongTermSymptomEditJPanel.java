package de.ollie.healthtracker.gui.swing.edit.longtermsymptom;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.LongTermSymptom;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JCheckBox;
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
public class LongTermSymptomEditJPanel extends AbstractEditPanel<LongTermSymptom> {

	public static final String BODY_PART_ITEM_PROVIDER_ID = "body-part-item-provider";

	private JTextField textFieldStartDate;
	private JTextField textFieldEndDate;
	private JComboBox<BodyPart> comboBoxBodyPart;
	private JTextField textFieldDescription;
	private JCheckBox checkBoxUnverified;

	public LongTermSymptomEditJPanel(LongTermSymptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Start Date:", "End Date:", "Body Part:", "Description:", "Unverified:");
	}

	@Override
	protected JPanel createComponentPanel(LongTermSymptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(5, 1, HGAP, VGAP));
		textFieldStartDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getStartDate()), 40);
		p.add(textFieldStartDate);
		textFieldEndDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getEndDate()), 40);
		p.add(textFieldEndDate);
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
		checkBoxUnverified = new JCheckBox();
		checkBoxUnverified.setSelected(toEdit.isUnverified());
		p.add(checkBoxUnverified);
		return p;
	}

	@Override
	public LongTermSymptom getCurrentContent() {
		return new LongTermSymptom()
			.setId(toEdit.getId())
			.setStartDate(DateTimeUtil.dateFromString(textFieldStartDate.getText()))
			.setEndDate(DateTimeUtil.dateFromString(textFieldEndDate.getText()))
			.setBodyPart(((BodyPart) comboBoxBodyPart.getSelectedItem()))
			.setDescription(textFieldDescription.getText())
			.setUnverified(checkBoxUnverified.isSelected());
	}
}
