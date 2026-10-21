package de.ollie.healthtracker.gui.swing.chart.bloodpressure;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.gui.swing.event.BloodPressureMeasurementChangeNotifier;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.List;
import java.util.function.IntFunction;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * An internal frame that displays a {@link BloodPressureChartJComponent} for the daily blood pressure values of the number
 * of days selected in the frame's day spinner (ending today). The chart is (re)loaded from {@code dataProvider} when the
 * frame opens, whenever the selected number of days changes and whenever the
 * {@link BloodPressureMeasurementChangeNotifier} signals a change, so it stays in sync with the measurement data. The
 * change listener is removed again when the frame is closed.
 */
public class BloodPressureChartJInternalFrame extends JInternalFrame {

	private static final int MIN_DAYS = 1;
	private static final int MAX_DAYS = 366;

	public BloodPressureChartJInternalFrame(
		JDesktopPane desktopPane,
		IntFunction<List<BloodPressureChartData>> dataProvider,
		int days,
		BloodPressureMeasurementChangeNotifier changeNotifier
	) {
		super("Blood Pressure Chart", true, true, true, true);
		BloodPressureChartJComponent chart = new BloodPressureChartJComponent();
		JSpinner daysSpinner = new JSpinner(new SpinnerNumberModel(days, MIN_DAYS, MAX_DAYS, 1));
		Runnable reload = () -> {
			int selectedDays = ((Number) daysSpinner.getValue()).intValue();
			chart.setData(dataProvider.apply(selectedDays), selectedDays, LocalDate.now());
		};
		daysSpinner.addChangeListener(e -> reload.run());
		reload.run();
		changeNotifier.addListener(reload);
		addInternalFrameListener(
			new InternalFrameAdapter() {
				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					changeNotifier.removeListener(reload);
				}
			}
		);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> dispose());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		buttonPanel.add(new JLabel("Days:"));
		buttonPanel.add(daysSpinner);
		buttonPanel.add(buttonClose);
		JPanel contentPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		contentPanel.add(chart, BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(contentPanel);
		setBounds(10, 10, 700, 480);
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}
}
