package app.fitness.repositories;

import app.fitness.entities.PlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanExerciseRepository extends JpaRepository<PlanExercise, Long> {
}
