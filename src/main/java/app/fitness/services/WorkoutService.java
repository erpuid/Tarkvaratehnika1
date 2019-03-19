package app.fitness.services;


import app.fitness.entities.Exercise;
import app.fitness.entities.Workout;
import app.fitness.repositories.ExerciseRepository;
import app.fitness.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping("api/workouts")
    public List<Workout> getAllWorkouts(){
        return workoutRepository.findAll();
    }

    @GetMapping("api/workouts/{username}")
    public List<Workout> getWorkoutById(@PathVariable String username){
        System.out.println(username);
        return workoutRepository.findAllByUserName(username);
    }




    @GetMapping("api/exercises")
    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }


    @PostMapping("api/workouts")
    public Workout saveWorkout(@RequestBody Workout workout) {
        System.out.println(workout.getUserName());
        for (Exercise ex: workout.getExercises()) {
            ex.setWorkout(workout);
        }
        return workoutRepository.save(workout);
    }


    @GetMapping("api/exercises/{id}")
    public List<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findAllByWorkoutId(id);
    }

}
