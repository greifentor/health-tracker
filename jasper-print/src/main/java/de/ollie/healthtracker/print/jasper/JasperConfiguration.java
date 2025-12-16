package de.ollie.healthtracker.print.jasper;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
class JasperConfiguration {

	@Value("${print.jasper.path}")
	private String jasperPath;
}
