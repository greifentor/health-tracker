package de.ollie.healthtracker.gui.swing.edit.meatproduct;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.MeatProduct;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MeatProductEditJPanel extends AbstractEditPanel<MeatProduct> {

	public static final String MEAT_TYPE_ITEM_PROVIDER_ID = "meat-type-item-provider";

	private JTextField textFieldDescription;
	private JComboBox<MeatType> comboBoxMeatType;
	private JSpinner spinnerAmountInGr;

	public MeatProductEditJPanel(MeatProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Description:", "Meat Type:", "Amount In Gr:");
	}

	@Override
	protected JPanel createComponentPanel(MeatProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
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
		SpinnerModel spinnerModelAmountInGr = new SpinnerNumberModel(toEdit.getAmountInGr(), 0, 1000, 1);
		spinnerAmountInGr = new JSpinner(spinnerModelAmountInGr);
		p.add(spinnerAmountInGr);
		return p;
	}

	@Override
	public MeatProduct getCurrentContent() {
		return new MeatProduct()
			.setId(toEdit.getId())
			.setDescription(textFieldDescription.getText())
			.setMeatType(((MeatType) comboBoxMeatType.getSelectedItem()))
			.setAmountInGr((Integer) spinnerAmountInGr.getValue());
	}
}
