package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Repository
public interface CommentDboRepository extends JpaRepository<CommentDbo, UUID> {
	@Query("SELECT dbo FROM CommentDbo dbo ORDER BY dbo.dateOfRecording DESC")
	List<CommentDbo> findAllOrdered();

	@Query("SELECT dbo FROM CommentDbo dbo WHERE dbo.content LIKE CONCAT('%', :content, '%')")
	List<CommentDbo> findAllByContentMatch(String content);

	@Query(
		"SELECT dbo FROM CommentDbo dbo WHERE dbo.dateOfRecording >= :from AND dbo.dateOfRecording <= :to ORDER BY dbo.dateOfRecording, dbo.commentType.name"
	)
	List<CommentDbo> findAllBetweenFromAndToOrderByDateOfRecordingAndCommentTypeName(LocalDate from, LocalDate to);
}
