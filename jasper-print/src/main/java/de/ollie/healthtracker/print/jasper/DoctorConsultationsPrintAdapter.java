package de.ollie.healthtracker.print.jasper;

import de.ollie.healthtracker.core.service.exception.PrintReportException;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import de.ollie.healthtracker.core.service.port.print.PrintPort;
import de.ollie.healthtracker.print.jasper.po.DoctorConsultationItemPO;
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
class DoctorConsultationsPrintAdapter implements PrintPort {

	private final DoctorConsultationReportDataCollector doctorConsultationReportDataCollector;
	private final JasperConfiguration jasperConfiguration;

	@Override
	public Details getDetails() {
		return new Details("jasper-doctor-consultations", "Jasper Reports Printing (Doctor Consultations)");
	}

	@Override
	public byte[] print(HealthTrackingReport report, Map<String, Object> parameters) {
		try (ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream()) {
			List<DoctorConsultationItemPO> consultations = doctorConsultationReportDataCollector.collect();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(consultations, true);
			JasperPrint document = JasperFillManager.fillReport(
				jasperConfiguration.getJasperPath() + "DoctorConsultations.jasper",
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
