package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.ExerciseDbo;
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
public interface ExerciseDboRepository extends JpaRepository<ExerciseDbo, UUID> {
	@Query("SELECT dbo FROM ExerciseDbo dbo ORDER BY dbo.name")
	List<ExerciseDbo> findAllOrdered();

	@Query("SELECT dbo FROM ExerciseDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<ExerciseDbo> findAllByNameMatch(String name);
}
