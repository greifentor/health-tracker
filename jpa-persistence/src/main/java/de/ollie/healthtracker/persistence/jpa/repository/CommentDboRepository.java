package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDboRepository extends JpaRepository<CommentDbo, UUID> {
	@Query("SELECT c FROM CommentDbo c ORDER BY c.dateOfRecording, c.timeOfRecording")
	List<CommentDbo> findAllOrdered();
}
