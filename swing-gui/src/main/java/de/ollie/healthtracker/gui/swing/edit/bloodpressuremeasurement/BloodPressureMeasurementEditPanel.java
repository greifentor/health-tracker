package de.ollie.healthtracker.gui.swing.edit.bloodpressuremeasurement;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class BloodPressureMeasurementEditPanel extends AbstractEditPanel<BloodPressureMeasurement> {

	private JCheckBox checkBoxIrregularHeartbeat;
	private JComboBox<BloodPressureMeasurementStatus> comboBoxStatus;
	private JSpinner spinnerDiaMmHg;
	private JSpinner spinnerPulsePerMinute;
	private JSpinner spinnerSysMmHg;
	private JTextField textFieldDateOfRecording;
	private JTextField textFieldTimeOfRecording;

	public BloodPressureMeasurementEditPanel(
		BloodPressureMeasurement toEdit,
		Map<String, ItemProvider<?>> itemProviders
	) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		p.add(
			createLabelSubPanel(
				"Date of Recording:",
				"Time of Recording:",
				"SYS mmHg",
				"DIA mmHg",
				"Pulse per Minute",
				"Irregular Heartbeat",
				"Status"
			)
		);
		return p;
	}

	@Override
	protected JPanel createComponentPanel(BloodPressureMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(7, 1, HGAP, VGAP));
		checkBoxIrregularHeartbeat = new JCheckBox();
		checkBoxIrregularHeartbeat.setSelected(toEdit.isIrregularHeartbeat());
		comboBoxStatus = new JComboBox<>(BloodPressureMeasurementStatus.values());
		comboBoxStatus.setSelectedItem(toEdit.getStatus());
		spinnerDiaMmHg = new JSpinner(new SpinnerNumberModel(toEdit.getDiaMmHg(), 0, 150, 1));
		spinnerPulsePerMinute = new JSpinner(new SpinnerNumberModel(toEdit.getPulsePerMinute(), 0, 250, 1));
		spinnerSysMmHg = new JSpinner(new SpinnerNumberModel(toEdit.getSysMmHg(), 0, 250, 1));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()));
		textFieldTimeOfRecording = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTimeOfRecording()));
		p.add(textFieldDateOfRecording);
		p.add(textFieldTimeOfRecording);
		p.add(spinnerSysMmHg);
		p.add(spinnerDiaMmHg);
		p.add(spinnerPulsePerMinute);
		p.add(comboBoxStatus);
		p.add(checkBoxIrregularHeartbeat);
		return p;
	}

	@Override
	public BloodPressureMeasurement getCurrentContent() {
		return new BloodPressureMeasurement()
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setDiaMmHg(((Number) spinnerDiaMmHg.getValue()).intValue())
			.setId(toEdit.getId())
			.setIrregularHeartbeat(checkBoxIrregularHeartbeat.isSelected())
			.setPulsePerMinute(((Number) spinnerPulsePerMinute.getValue()).intValue())
			.setStatus((BloodPressureMeasurementStatus) comboBoxStatus.getSelectedItem())
			.setSysMmHg(((Number) spinnerSysMmHg.getValue()).intValue())
			.setTimeOfRecording(DateTimeUtil.timeFromString(textFieldTimeOfRecording.getText()));
	}
}
