package app.fitness.repositories;

import app.fitness.Entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByWorkoutId(Long workoutId);

    @Transactional
    @Modifying
    @Query("update Exercise ex SET ex.workout.id = :workout_id WHERE ex.workout.id = NULL")
    List<Exercise> setWorkoutId(@Param("workout_id") Long workoutId);
}
