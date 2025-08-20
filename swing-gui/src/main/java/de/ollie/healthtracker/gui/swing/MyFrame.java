package de.ollie.healthtracker.gui.swing;

import de.ollie.healthtracker.core.service.DoctorService;
import jakarta.inject.Named;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.springframework.beans.factory.annotation.Autowired;

@Named
public class MyFrame extends JFrame {

	@Autowired
	private DoctorService doctorService;

	public MyFrame() {
		super("Swing + Spring Boot");
		JDesktopPane desktopPane = new JDesktopPane();
		JInternalFrame internalFrame = new JInternalFrame("Test", true, true, true, true);
		internalFrame.setBounds(50, 50, 300, 200);
		internalFrame.setVisible(true);
		desktopPane.add(internalFrame);

		setContentPane(desktopPane);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void showFrame() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		doctorService.listDoctors().forEach(System.out::println);
		setVisible(true);
	}
}
