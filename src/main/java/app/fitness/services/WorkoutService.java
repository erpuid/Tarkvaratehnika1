package app.fitness.services;


import app.fitness.entities.Exercise;
import app.fitness.entities.Workout;
import app.fitness.repositories.ExerciseRepository;
import app.fitness.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserService userService;

    public List<Workout> getAllWorkouts(){
        return workoutRepository.findAll();
    }

    public List<Workout> getWorkoutByUsername(String username){
        if (userService.getUsername().equals(username)) {
            return workoutRepository.findAllByUserName(username);
        }
        return null;
    }

    public Workout saveWorkout(Workout workout) {
        for (Exercise ex: workout.getExercises()) {
            ex.setWorkout(workout);
        }
        return workoutRepository.save(workout);
    }
}
