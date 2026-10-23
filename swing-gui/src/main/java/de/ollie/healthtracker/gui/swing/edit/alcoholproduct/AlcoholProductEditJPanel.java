package de.ollie.healthtracker.gui.swing.edit.alcoholproduct;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.core.service.model.AlcoholType;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class AlcoholProductEditJPanel extends AbstractEditPanel<AlcoholProduct> {

	public static final String ALCOHOL_TYPE_ITEM_PROVIDER_ID = "alcohol-type-item-provider";

	private JTextField textFieldName;
	private JSpinner spinnerPercentVol;
	private JComboBox<AlcoholType> comboBoxAlcoholType;

	public AlcoholProductEditJPanel(AlcoholProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:", "Percent Vol:", "Alcohol Type:");
	}

	@Override
	protected JPanel createComponentPanel(AlcoholProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		spinnerPercentVol = createDecimalSpinner(toEdit.getPercentVol(), 0, 1000000, 0.1, 1);
		p.add(spinnerPercentVol);
		List<AlcoholType> listAlcoholType =
			((ItemProvider<AlcoholType>) itemProviders.get(ALCOHOL_TYPE_ITEM_PROVIDER_ID)).getItem();
		comboBoxAlcoholType = new JComboBox<>(listAlcoholType.toArray(new AlcoholType[listAlcoholType.size()]));
		comboBoxAlcoholType.setSelectedItem(toEdit.getAlcoholType());
		comboBoxAlcoholType.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxAlcoholType);
		return p;
	}

	@Override
	public AlcoholProduct getCurrentContent() {
		return new AlcoholProduct()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setPercentVol(decimalValueOf(spinnerPercentVol))
			.setAlcoholType(((AlcoholType) comboBoxAlcoholType.getSelectedItem()));
	}
}
