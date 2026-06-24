package de.ollie.healthtracker.gui.swing.chart.weight;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.gui.swing.event.WeightMeasurementChangeNotifier;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * An internal frame that displays a {@link WeightChartJComponent} for the daily weight measurements of a month, using
 * the given (configurable) value range. The chart is (re)loaded from {@code dataSupplier} when the frame opens and again
 * whenever the {@link WeightMeasurementChangeNotifier} signals a change, so it stays in sync with the weight measurement
 * data. The change listener is removed again when the frame is closed.
 */
public class WeightChartJInternalFrame extends JInternalFrame {

	public WeightChartJInternalFrame(
		JDesktopPane desktopPane,
		Supplier<List<WeightChartData>> dataSupplier,
		int daysInMonth,
		double minWeight,
		double maxWeight,
		WeightMeasurementChangeNotifier changeNotifier
	) {
		super("Weight Chart", true, true, true, true);
		WeightChartJComponent chart = new WeightChartJComponent();
		chart.setValueRange(minWeight, maxWeight);
		chart.setData(dataSupplier.get(), daysInMonth);
		Runnable changeListener = () -> chart.setData(dataSupplier.get(), daysInMonth);
		changeNotifier.addListener(changeListener);
		addInternalFrameListener(
			new InternalFrameAdapter() {
				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					changeNotifier.removeListener(changeListener);
				}
			}
		);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> dispose());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
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
