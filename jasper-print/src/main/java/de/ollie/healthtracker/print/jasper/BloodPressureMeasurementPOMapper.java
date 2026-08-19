package de.ollie.healthtracker.print.jasper;

import de.ollie.healthtracker.core.service.WhoBloodPressureClassificationService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import de.ollie.healthtracker.print.jasper.po.BloodPressureMeasurementPO;
import jakarta.inject.Named;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;

/** Maps {@link BloodPressureMeasurement}s to {@link BloodPressureMeasurementPO}s with German date/time and WHO labels. */
@Named
@RequiredArgsConstructor
class BloodPressureMeasurementPOMapper {

	private static final DateTimeFormatter GERMAN_DATE_FORMATTER = DateTimeFormatter
		.ofPattern("dd.MM.yyyy")
		.withLocale(Locale.GERMANY);
	private static final DateTimeFormatter GERMAN_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	private final WhoBloodPressureClassificationService whoBloodPressureClassificationService;

	List<BloodPressureMeasurementPO> map(List<BloodPressureMeasurement> measurements) {
		return measurements.stream().map(this::map).toList();
	}

	private BloodPressureMeasurementPO map(BloodPressureMeasurement bpm) {
		return new BloodPressureMeasurementPO()
			.setClassificationWho(
				getGermanString(
					whoBloodPressureClassificationService.calculateClassification(bpm.getSysMmHg(), bpm.getDiaMmHg())
				)
			)
			.setDate(getGermanDateTime(bpm))
			.setDiaMmHg("" + bpm.getDiaMmHg())
			.setIrregularHeartbeat(bpm.isIrregularHeartbeat())
			.setPulsePerMinute("" + bpm.getPulsePerMinute())
			.setSysMmHg("" + bpm.getSysMmHg());
	}

	private String getGermanString(WhoBloodPressureClassification status) {
		switch (status) {
			case OPTIMAL:
				return "Optimal";
			case NORMAL:
				return "Normal";
			case HIGH_NORMAL:
				return "Hoch-normal";
			case HYPERTENSION_GRADE_1:
				return "Hypertonie Grad 1";
			case HYPERTENSION_GRADE_2:
				return "Hypertonie Grad 2";
			case HYPERTENSION_GRADE_3:
				return "Hypertonie Grad 3";
			default:
				return "???";
		}
	}

	private String getGermanDateTime(BloodPressureMeasurement bpm) {
		return (
			bpm.getDateOfRecording().format(GERMAN_DATE_FORMATTER) +
			" " +
			bpm.getTimeOfRecording().format(GERMAN_TIME_FORMATTER)
		);
	}
}
