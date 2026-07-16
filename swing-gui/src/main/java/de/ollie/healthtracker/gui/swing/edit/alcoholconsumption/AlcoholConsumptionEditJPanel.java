package de.ollie.healthtracker.gui.swing.edit.alcoholconsumption;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class AlcoholConsumptionEditJPanel extends AbstractEditPanel<AlcoholConsumption> {

	public static final String ALCOHOL_PRODUCT_ITEM_PROVIDER_ID = "alcohol-product-item-provider";

	private JTextField textFieldDate;
	private JComboBox<AlcoholProduct> comboBoxAlcoholProduct;
	private JTextField textFieldComment;
	private JSpinner spinnerLiter;

	public AlcoholConsumptionEditJPanel(AlcoholConsumption toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Date:", "Alcohol Product:", "Comment:", "Liter:");
	}

	@Override
	protected JPanel createComponentPanel(AlcoholConsumption toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(4, 1, HGAP, VGAP));
		textFieldDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDate()), 40);
		p.add(textFieldDate);
		List<AlcoholProduct> listAlcoholProduct =
			((ItemProvider<AlcoholProduct>) itemProviders.get(ALCOHOL_PRODUCT_ITEM_PROVIDER_ID)).getItem();
		comboBoxAlcoholProduct = new JComboBox<>(listAlcoholProduct.toArray(new AlcoholProduct[listAlcoholProduct.size()]));
		comboBoxAlcoholProduct.setSelectedItem(toEdit.getAlcoholProduct());
		comboBoxAlcoholProduct.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxAlcoholProduct);
		textFieldComment = new JTextField(toEdit.getComment(), 40);
		p.add(textFieldComment);
		spinnerLiter = createDecimalSpinner(toEdit.getLiter(), 0, 1000000, 0.1, 1);
		p.add(spinnerLiter);
		return p;
	}

	@Override
	public AlcoholConsumption getCurrentContent() {
		return new AlcoholConsumption()
			.setId(toEdit.getId())
			.setDate(DateTimeUtil.dateFromString(textFieldDate.getText()))
			.setAlcoholProduct(((AlcoholProduct) comboBoxAlcoholProduct.getSelectedItem()))
			.setComment(textFieldComment.getText())
			.setLiter(decimalValueOf(spinnerLiter));
	}
}
