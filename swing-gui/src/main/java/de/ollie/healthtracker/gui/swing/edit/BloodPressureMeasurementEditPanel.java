package de.ollie.healthtracker.gui.swing.edit;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.gui.swing.Editor;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class BloodPressureMeasurementEditPanel extends JPanel implements Editor<BloodPressureMeasurement> {

	private JCheckBox checkBoxIrregularHeartbeat;
	private JComboBox<BloodPressureMeasurementStatus> comboBoxStatus;
	private JSpinner spinnerDiaMmHg;
	private JSpinner spinnerPulsePerMinute;
	private JSpinner spinnerSysMmHg;
	private JTextField textFieldDateOfRecording;
	private JTextField textFieldTimeOfRecording;

	private BloodPressureMeasurement toEdit;

	public BloodPressureMeasurementEditPanel(
		BloodPressureMeasurement toEdit,
		Map<String, ItemProvider<?>> itemProviders
	) {
		super(new BorderLayout(HGAP, VGAP));
		this.toEdit = toEdit;
		setBorder(new EmptyBorder(VGAP, HGAP, VGAP, HGAP));
		JPanel psub = new JPanel(new BorderLayout(HGAP, VGAP));
		psub.add(createLabelPanel(), BorderLayout.WEST);
		psub.add(createComponentPanel(toEdit), BorderLayout.CENTER);
		add(psub, BorderLayout.NORTH);
	}

	private JPanel createLabelPanel() {
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

	private JPanel createLabelSubPanel(String... labels) {
		JPanel p = new JPanel(new GridLayout(labels.length, 1, HGAP, VGAP));
		for (String label : labels) {
			p.add(new JLabel(label));
		}
		return p;
	}

	private JPanel createComponentPanel(BloodPressureMeasurement toEdit) {
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
