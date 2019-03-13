package app.fitness.services;


import app.fitness.Entities.Exercise;
import app.fitness.Entities.Workout;
import app.fitness.Entities.WorkoutPlan;
import app.fitness.repositories.ExerciseRepository;
import app.fitness.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RestController
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping("api/workouts")
    public List<Workout> getAllWorkouts(){
        System.out.println("Siin");
        //List<Workout> workouts = workoutRepository.findAll();
        //System.out.println(workouts.toString());
        return workoutRepository.findAll();
    }

    @GetMapping("api/workouts/{id}")
    public Optional<Workout> getWorkoutById(@PathVariable Long id){
        //Optional<Workout> workout = workoutRepository.findById(id);
        return workoutRepository.findById(id);
    }


    @GetMapping("api/exercises")
    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }


    @PostMapping("api/workouts")
    public Workout saveWorkout(@RequestBody Workout workout) {
        System.out.println(workout.getExercises());
        for (Exercise ex: workout.getExercises()) {
            ex.setWorkout(workout);
            //System.out.println(saved.getId());
        }
        //System.out.println(exerciseRepository.setWorkoutId(workout.getId()));
        return workoutRepository.save(workout);
    }


    @GetMapping("api/exercises/{id}")
    public List<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findAllByWorkoutId(id);
    }

}
