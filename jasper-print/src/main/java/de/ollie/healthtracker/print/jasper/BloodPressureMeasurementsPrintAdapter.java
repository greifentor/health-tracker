package de.ollie.healthtracker.print.jasper;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.exception.PrintReportException;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import de.ollie.healthtracker.core.service.port.print.PrintPort;
import de.ollie.healthtracker.print.jasper.po.BloodPressureMeasurementPO;
import jakarta.inject.Named;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * A print port that renders a standalone list of all blood pressure measurements within the report's time interval,
 * using the {@code BloodPressureMeasurements} Jasper report. Selected via the {@code jasper-blood-pressure} print port
 * id.
 */
@Named
@RequiredArgsConstructor
class BloodPressureMeasurementsPrintAdapter implements PrintPort {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final BloodPressureMeasurementPOMapper bloodPressureMeasurementPOMapper;
	private final JasperConfiguration jasperConfiguration;

	@Override
	public Details getDetails() {
		return new Details("jasper-blood-pressure", "Jasper Reports Printing (Blood Pressure Measurements)");
	}

	@Override
	public byte[] print(HealthTrackingReport report, Map<String, Object> parameters) {
		try (ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream()) {
			List<BloodPressureMeasurementPO> measurements = bloodPressureMeasurementPOMapper.map(
				bloodPressureMeasurementService.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(
					report.getFrom(),
					report.getTo()
				)
			);
			parameters.put(
				"HEAD_LINE",
				"Blutdruckwerte (" +
				DateTimeUtil.dateToString(report.getFrom()) +
				" - " +
				DateTimeUtil.dateToString(report.getTo()) +
				")"
			);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(measurements, true);
			JasperPrint document = JasperFillManager.fillReport(
				jasperConfiguration.getJasperPath() + "BloodPressureMeasurements.jasper",
				parameters,
				dataSource
			);
			JasperExportManager.exportReportToPdfStream(document, pdfReportStream);
			return pdfReportStream.toByteArray();
		} catch (Exception e) {
			throw new PrintReportException(e.getMessage(), e);
		}
	}
}
