package de.ollie.healthtracker.gui.swing.edit.alcoholproduct;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
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

	private JTextField textFieldName;
	private JSpinner spinnerPercentVol;

	public AlcoholProductEditJPanel(AlcoholProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:", "Percent Vol:");
	}

	@Override
	protected JPanel createComponentPanel(AlcoholProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		spinnerPercentVol = createDecimalSpinner(toEdit.getPercentVol(), 0, 1000000, 0.1, 1);
		p.add(spinnerPercentVol);
		return p;
	}

	@Override
	public AlcoholProduct getCurrentContent() {
		return new AlcoholProduct()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setPercentVol(decimalValueOf(spinnerPercentVol));
	}
}
