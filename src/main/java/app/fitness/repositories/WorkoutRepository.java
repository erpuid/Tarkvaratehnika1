package app.fitness.repositories;

import app.fitness.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<Workout> findById(Long id);

    List<Workout> findAllByUserName(String userName);
}
