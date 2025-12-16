package de.ollie.healthtracker.core.service.port.print;

import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public interface PrintPort {
	@EqualsAndHashCode
	@Getter
	@RequiredArgsConstructor
	@ToString
	public static class Details {

		private final String id;
		private final String description;
	}

	Details getDetails();

	byte[] print(HealthTrackingReport report, Map<String, Object> parameters);
}
