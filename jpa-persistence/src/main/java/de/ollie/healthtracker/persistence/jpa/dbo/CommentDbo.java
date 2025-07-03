package de.ollie.healthtracker.persistence.jpa.dbo;

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
@Entity(name = "CommentDbo")
@Table(name = "COMMENT")
public class CommentDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "DATE_OF_RECORDING", nullable = false)
	private LocalDate dateOfRecording;

	@Column(name = "TIME_OF_RECORDING", nullable = false)
	private LocalTime timeOfRecording;

	@Column(name = "CONTENT", nullable = false)
	private String content;
}
