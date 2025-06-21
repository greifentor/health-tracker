package de.ollie.bptracker.persistence.jpa.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
@Entity(name = "BloodPressureMeasurement")
@Table(name = "BLOOD_PRESURE_MEASUREMENT")
public class BloodPressureMeasurementDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "DATE_OF_RECORDING", nullable = false)
	private LocalDate dateOfRecording;

	@Column(name = "TIME_OF_RECORDING", nullable = false)
	private LocalTime timeOfRecording;

	@Column(name = "DIA_MM_HG", nullable = false)
	private int diaMmHg;

	@Column(name = "SYS_MM_HG", nullable = false)
	private int sysMmHg;

	@Column(name = "PULSE_PER_MINUTE", nullable = false)
	private int pulsePerMinute;

	@Column(name = "STATUS", nullable = false)
	private BloodPressureMeasurementStatusDbo status;
}
