package de.ollie.healthtracker.gui.swing.edit.alcoholproduct;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

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
		spinnerPercentVol =
			new JSpinner(
				new SpinnerNumberModel(
					toEdit.getPercentVol() == null ? 0.0 : toEdit.getPercentVol().doubleValue(),
					0.0,
					100,
					0.1
				)
			);
		p.add(spinnerPercentVol);
		return p;
	}

	@Override
	public AlcoholProduct getCurrentContent() {
		return new AlcoholProduct()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setPercentVol(new BigDecimal(((Number) spinnerPercentVol.getValue()).doubleValue()));
	}
}
