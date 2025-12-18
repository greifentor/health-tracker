package de.ollie.healthtracker.print.jasper;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.exception.PrintReportException;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import de.ollie.healthtracker.core.service.port.print.PrintPort;
import de.ollie.healthtracker.print.jasper.po.DataPerDayPO;
import de.ollie.healthtracker.print.jasper.po.HealthTrackingReportPO;
import jakarta.inject.Named;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@RequiredArgsConstructor
class JasperPrintAdapter implements PrintPort {

	private final JasperConfiguration jasperConfiguration;

	@Override
	public Details getDetails() {
		return new Details("jasper", "Jasper Reports Printing");
	}

	@Override
	public byte[] print(HealthTrackingReport report, Map<String, Object> parameters) {
		String jasperPath = jasperConfiguration.getJasperPath();
		try (ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream()) {
			parameters.put(
				"SUBREPORT_DIR",
				"/home/ollie/Eclipse-Workspace/health-tracker/jasper-print/src/main/resources/jasper/src/"
			);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(mapToPO(report)), true);
			JasperPrint document = createDocument(jasperPath, dataSource, parameters);
			JasperExportManager.exportReportToPdfStream(document, pdfReportStream);
			return pdfReportStream.toByteArray();
		} catch (Exception e) {
			throw new PrintReportException(e.getMessage(), e);
		}
	}

	private HealthTrackingReportPO mapToPO(HealthTrackingReport report) {
		return new HealthTrackingReportPO()
			.setFrom(DateTimeUtil.dateToString(report.getFrom()))
			.setTo(DateTimeUtil.dateToString(report.getTo()))
			.setDataPerDayOrderedByDate(
				report
					.getDataPerDayOrderedByDate()
					.stream()
					.map(dpd ->
						new DataPerDayPO()
							.setDate(DateTimeUtil.dateToString(dpd.getDate()))
							.setComment(dpd.getComment().getContent())
					)
					.toList()
			);
	}

	private JasperPrint createDocument(
		String jasperPath,
		JRBeanCollectionDataSource dataSource,
		Map<String, Object> parameters
	) throws JRException {
		return JasperFillManager.fillReport(jasperPath + "HealthReport.jasper", parameters, dataSource);
	}
}
