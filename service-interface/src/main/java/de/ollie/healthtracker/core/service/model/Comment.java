package de.ollie.healthtracker.core.service.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Accessors(chain = true)
@Data
@Generated
public class Comment {

	private UUID id;
	private CommentType commentType;
	private String content;
	private LocalDate dateOfRecording;

}