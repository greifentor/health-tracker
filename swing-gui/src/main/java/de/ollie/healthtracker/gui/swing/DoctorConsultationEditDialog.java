package de.ollie.healthtracker.gui.swing;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DoctorConsultationEditDialog extends BaseEditDialog<DoctorConsultation> {

	private static final String DOCTORS_ITEM_PROVIDER_ID = "doctors-item-provider";

	private JComboBox<Doctor> comboBoxDoctor;
	private JTextArea textAreaReason;
	private JTextArea textAreaResult;
	private JTextField textFieldDate;
	private JTextField textFieldTime;

	public DoctorConsultationEditDialog(
		String title,
		DoctorConsultation toEdit,
		EditDialogComponentFactory componentFactory,
		Observer<DoctorConsultation> observer,
		ItemProvider<Doctor> doctors
	) {
		super(title, toEdit, componentFactory, observer, Map.of(DOCTORS_ITEM_PROVIDER_ID, doctors));
	}

	@Override
	JPanel createEditorPanel(DoctorConsultation toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		JPanel psub = new JPanel(new BorderLayout(HGAP, VGAP));
		psub.add(createLabelPanel(), BorderLayout.WEST);
		psub.add(
			createComponentPanel(toEdit, (ItemProvider<Doctor>) itemProviders.get(DOCTORS_ITEM_PROVIDER_ID)),
			BorderLayout.CENTER
		);
		p.add(psub, BorderLayout.NORTH);
		return p;
	}

	private JPanel createLabelPanel() {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(createLabelSubPanel("Date of Consuldation", "Time ot Consultation", "Doctor"));
		p.add(createLabelSubPanel("Reason", "", ""));
		p.add(createLabelSubPanel("Result", "", ""));
		return p;
	}

	private JPanel createLabelSubPanel(String label0, String label1, String label2) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(new JLabel(label0));
		p.add(new JLabel(label1));
		p.add(new JLabel(label2));
		return p;
	}

	private JPanel createComponentPanel(DoctorConsultation toEdit, ItemProvider<Doctor> doctors) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(createDateTimeAndDoctorPanel(toEdit, doctors));
		p.add(createReasonPanel(toEdit));
		p.add(createResultPanel(toEdit));
		return p;
	}

	private JPanel createDateTimeAndDoctorPanel(DoctorConsultation toEdit, ItemProvider<Doctor> doctors) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		textFieldDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDate()));
		textFieldTime = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTime()));
		List<Doctor> doctorList = doctors.getItem();
		comboBoxDoctor = new JComboBox<>(doctorList.toArray(new Doctor[doctorList.size()]));
		p.add(textFieldDate);
		p.add(textFieldTime);
		p.add(comboBoxDoctor);
		return p;
	}

	private JPanel createReasonPanel(DoctorConsultation toEdit) {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		textAreaReason = new JTextArea(toEdit.getReason(), 5, 40);
		p.add(textAreaReason);
		return p;
	}

	private JPanel createResultPanel(DoctorConsultation toEdit) {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		textAreaResult = new JTextArea(toEdit.getResult(), 5, 40);
		p.add(textAreaResult);
		return p;
	}

	@Override
	public DoctorConsultation getCurrentContent() {
		return new DoctorConsultation()
			.setDate(DateTimeUtil.dateFromString(textFieldDate.getText()))
			.setDoctor((Doctor) comboBoxDoctor.getSelectedItem())
			.setId(getToEdit().getId())
			.setReason(textAreaReason.getText())
			.setResult(textAreaResult.getText())
			.setTime(DateTimeUtil.timeFromString(textFieldTime.getText()));
	}
}
