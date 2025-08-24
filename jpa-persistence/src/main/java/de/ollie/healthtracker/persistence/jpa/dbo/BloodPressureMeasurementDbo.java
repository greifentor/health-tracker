package de.ollie.healthtracker.persistence.jpa.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Accessors(chain = true)
@Data
@Generated
@Entity(name = "BloodPressureMeasurementDbo")
@Table(name = "BLOOD_PRESSURE_MEASUREMENT")
public class BloodPressureMeasurementDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "DATE_OF_RECORDING", nullable = false)
	private LocalDate dateOfRecording;

	@Column(name = "DIA_MM_HG", nullable = true)
	private int diaMmHg;

	@Column(name = "PULSE_PER_MINUTE", nullable = true)
	private int pulsePerMinute;

	@Column(name = "SYS_MM_HG", nullable = true)
	private int sysMmHg;

	@Column(name = "TIME_OF_RECORDING", nullable = false)
	private LocalTime timeOfRecording;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private BloodPressureMeasurementStatusDbo status;

	@Column(name = "IRREGULAR_HEARTBEAT", nullable = true)
	private boolean irregularHeartbeat;
}
