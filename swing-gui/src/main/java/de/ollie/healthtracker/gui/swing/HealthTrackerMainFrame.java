package de.ollie.healthtracker.gui.swing;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.gui.swing.select.BloodPressureMeasurementSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.DoctorConsultationSelectJInternalFrame;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class HealthTrackerMainFrame extends JFrame implements ActionListener {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final DoctorConsultationService doctorConsultationService;
	private final DoctorService doctorService;
	private final EditDialogComponentFactory editDialogComponentFactory;

	private JDesktopPane desktopPane;
	private JMenuItem menuItemFileQuit;
	private JMenuItem menuItemEditBloodPressureMeasurement;
	private JMenuItem menuItemEditDoctorConsultation;

	@PostConstruct
	void postConstruct() {
		desktopPane = new JDesktopPane();
		desktopPane.setMinimumSize(new Dimension(200, 100));
		JPanel mainPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		mainPanel.add(desktopPane, BorderLayout.CENTER);
		setJMenuBar(createJMenuBar());
		setContentPane(mainPanel);
		setBounds(100, 100, 800, 600);
	}

	public void showFrame() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Health-Tracker");
		setVisible(true);
	}

	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuItemFileQuit = createMenuItem("Quit", this);
		menu.add(menuItemFileQuit);
		menuBar.add(menu);
		menu = new JMenu("Edit");
		menuItemEditBloodPressureMeasurement = createMenuItem("Blood Pressure Measurement", this);
		menu.add(menuItemEditBloodPressureMeasurement);
		menuItemEditDoctorConsultation = createMenuItem("Doctor Consultation", this);
		menu.add(menuItemEditDoctorConsultation);
		menuBar.add(menu);
		return menuBar;
	}

	private JMenuItem createMenuItem(String text, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.addActionListener(listener);
		return menuItem;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItemEditBloodPressureMeasurement) {
			new BloodPressureMeasurementSelectJInternalFrame(
				bloodPressureMeasurementService,
				doctorService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditDoctorConsultation) {
			new DoctorConsultationSelectJInternalFrame(
				doctorConsultationService,
				doctorService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemFileQuit) {
			System.out.println("Cancel");
			System.exit(0);
		}
	}
}
