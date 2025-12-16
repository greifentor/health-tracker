package de.ollie.healthtracker.gui.swing;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
class SwingConfiguration {

	@Value("${print.pdf.temp.filename}")
	private String pdfTmpFilename;

	@Value("${print.pdf.viewer.call.application}")
	private String pdfViewerCallApplication;

	@Value("${print.pdf.viewer.call.parameters}")
	private String pdfViewerCallParameters;
}
