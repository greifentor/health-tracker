package de.ollie.healthtracker.core.service.port.print;

import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface PrintPort {
	@Getter
	@RequiredArgsConstructor
	public static class Details {

		private final String id;
		private final String description;
	}

	Details getDetails();

	void print(HealthTrackingReport report, Map<String, String> parameters);
}
