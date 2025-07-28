package de.ollie.healthtracker.persistence.jpa.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "DoctorConsultationDbo")
@Table(name = "DOCTOR_CONSULTATION")
public class DoctorConsultationDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "DATE", nullable = false)
	private LocalDate date;

	@Column(name = "TIME", nullable = false)
	private LocalTime time;

	@JoinColumn(name = "DOCTOR", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private DoctorDbo doctor;

	@Column(name = "REASON", nullable = false)
	private String reason;

	@Column(name = "RESULT", nullable = false)
	private String result;
}
