package de.ollie.healthtracker.gui.swing.chart.alcohol;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.gui.swing.event.AlcoholConsumptionChangeNotifier;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * An internal frame that displays an {@link AlcoholChartJComponent} for the daily alcohol consumption of the last
 * {@code days} days (ending today). The chart is (re)loaded from {@code dataSupplier} when the frame opens and again
 * whenever the {@link AlcoholConsumptionChangeNotifier} signals a change, so it stays in sync with the alcohol
 * consumption data. The change listener is removed again when the frame is closed.
 */
public class AlcoholChartJInternalFrame extends JInternalFrame {

	public AlcoholChartJInternalFrame(
		JDesktopPane desktopPane,
		Supplier<List<AlcoholChartData>> dataSupplier,
		int days,
		AlcoholConsumptionChangeNotifier changeNotifier
	) {
		super("Alcohol Chart", true, true, true, true);
		AlcoholChartJComponent chart = new AlcoholChartJComponent();
		Runnable changeListener = () -> chart.setData(dataSupplier.get(), days, LocalDate.now());
		changeListener.run();
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
