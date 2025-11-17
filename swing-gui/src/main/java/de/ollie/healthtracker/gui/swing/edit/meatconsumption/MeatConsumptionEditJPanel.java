package de.ollie.healthtracker.gui.swing.edit.meatconsumption;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatType;
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
public class MeatConsumptionEditJPanel extends AbstractEditPanel<MeatConsumption> {

	public static final String MEAT_TYPE_ITEM_PROVIDER_ID = "meat-type-item-provider";

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldDescription;
	private JComboBox<MeatType> comboBoxMeatType;
	private JTextField textFieldAmountInGr;

	public MeatConsumptionEditJPanel(MeatConsumption toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Date Of Recording:", "Description:", "Meat Type:", "Amount In Gr:");
	}

	@Override
	protected JPanel createComponentPanel(MeatConsumption toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(4, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		textFieldDescription = new JTextField(toEdit.getDescription(), 40);
		p.add(textFieldDescription);
		List<MeatType> listMeatType = ((ItemProvider<MeatType>) itemProviders.get(MEAT_TYPE_ITEM_PROVIDER_ID)).getItem();
		comboBoxMeatType = new JComboBox<>(listMeatType.toArray(new MeatType[listMeatType.size()]));
		comboBoxMeatType.setSelectedItem(toEdit.getMeatType());
		comboBoxMeatType.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxMeatType);
		textFieldAmountInGr = new JTextField("" + toEdit.getAmountInGr(), 40);
		return p;
	}

	@Override
	public MeatConsumption getCurrentContent() {
		return new MeatConsumption()
			.setId(toEdit.getId())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setDescription(textFieldDescription.getText())
			.setMeatType(((MeatType) comboBoxMeatType.getSelectedItem()))
			.setAmountInGr(Integer.parseInt(textFieldAmountInGr.getText()));
	}
}
