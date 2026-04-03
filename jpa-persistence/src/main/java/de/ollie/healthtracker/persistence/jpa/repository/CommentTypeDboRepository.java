package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.CommentTypeDbo;
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
public interface CommentTypeDboRepository extends JpaRepository<CommentTypeDbo, UUID> {
	@Query("SELECT dbo FROM CommentTypeDbo dbo ORDER BY dbo.name DESC")
	List<CommentTypeDbo> findAllOrdered();

	@Query("SELECT dbo FROM CommentTypeDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<CommentTypeDbo> findAllByNameMatch(String name);
}
