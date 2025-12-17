package de.ollie.healthtracker.gui.swing.external.viewer.pdf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
class ExternalPdfViewerConfiguration {

	@Value("${external.viewer.pdf.temp.filename}")
	private String pdfTmpFilename;

	@Value("${external.viewer.pdf.call.application}")
	private String pdfViewerCallApplication;

	@Value("${external.viewer.pdf.call.parameters}")
	private String pdfViewerCallParameters;
}
