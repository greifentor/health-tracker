package de.ollie.healthtracker.gui.swing.opentask;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.OpenTaskService;
import de.ollie.healthtracker.gui.swing.event.BloodPressureMeasurementChangeNotifier;
import de.ollie.healthtracker.gui.swing.event.WeightMeasurementChangeNotifier;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * An internal frame that lists the open tasks provided by the {@link OpenTaskService}. The tasks are (re)loaded from the
 * service when the frame opens and again whenever a blood pressure or weight measurement changes, so the list stays in
 * sync with the underlying data. The change listeners are removed again when the frame is closed.
 */
public class OpenTaskJInternalFrame extends JInternalFrame {

	public OpenTaskJInternalFrame(
		JDesktopPane desktopPane,
		OpenTaskService openTaskService,
		BloodPressureMeasurementChangeNotifier bloodPressureMeasurementChangeNotifier,
		WeightMeasurementChangeNotifier weightMeasurementChangeNotifier
	) {
		super("Open Tasks", true, true, true, true);
		DefaultListModel<String> listModel = new DefaultListModel<>();
		JList<String> taskList = new JList<>(listModel);
		Runnable reload = () -> {
			listModel.clear();
			openTaskService.findAllOpenTasks().forEach(task -> listModel.addElement(task.getDescription()));
		};
		reload.run();
		Runnable changeListener = () -> {
			reload.run();
			// Close the frame once the last open task has been done.
			if (listModel.isEmpty()) {
				dispose();
			}
		};
		bloodPressureMeasurementChangeNotifier.addListener(changeListener);
		weightMeasurementChangeNotifier.addListener(changeListener);
		addInternalFrameListener(
			new InternalFrameAdapter() {
				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					bloodPressureMeasurementChangeNotifier.removeListener(changeListener);
					weightMeasurementChangeNotifier.removeListener(changeListener);
				}
			}
		);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> dispose());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		buttonPanel.add(buttonClose);
		JPanel contentPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		contentPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(contentPanel);
		setBounds(10, 10, 500, 400);
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}
}
