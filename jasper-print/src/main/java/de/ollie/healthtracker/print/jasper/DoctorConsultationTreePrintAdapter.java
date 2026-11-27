package de.ollie.healthtracker.print.jasper;

import de.ollie.healthtracker.core.service.exception.PrintReportException;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import de.ollie.healthtracker.core.service.port.print.PrintPort;
import de.ollie.healthtracker.print.jasper.po.DoctorConsultationTreeItemPO;
import jakarta.inject.Named;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@RequiredArgsConstructor
class DoctorConsultationTreePrintAdapter implements PrintPort {

	private final DoctorConsultationTreeReportDataCollector doctorConsultationTreeReportDataCollector;
	private final JasperConfiguration jasperConfiguration;

	@Override
	public Details getDetails() {
		return new Details("jasper-doctor-consultation-tree", "Jasper Reports Printing (Doctor Consultation Tree)");
	}

	@Override
	public byte[] print(HealthTrackingReport report, Map<String, Object> parameters) {
		try (ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream()) {
			List<DoctorConsultationTreeItemPO> consultations = doctorConsultationTreeReportDataCollector.collect();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(consultations, true);
			JasperPrint document = JasperFillManager.fillReport(
				jasperConfiguration.getJasperPath() + "DoctorConsultationTree.jasper",
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
