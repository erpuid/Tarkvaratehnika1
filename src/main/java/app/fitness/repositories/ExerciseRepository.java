package app.fitness.repositories;

import app.fitness.Entities.Exercise;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByWorkoutId(Long workoutid);

    @Query("update Exercise ex SET ex.workoutId = :workout_id WHERE ex.workoutId = NULL")
    List<Exercise> setWorkoutId(@Param("workout_id") Long workoutId);
}
