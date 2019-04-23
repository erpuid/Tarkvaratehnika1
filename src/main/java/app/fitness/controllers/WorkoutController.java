package app.fitness.controllers;


import app.fitness.entities.Workout;
import app.fitness.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkoutController {


    @Autowired
    private WorkoutService service;


    @GetMapping("api/workouts")
    public List<Workout> getAllWorkouts(){
        return service.getAllWorkouts();
    }

    @GetMapping("api/workouts/{username}")
    public List<Workout> getWorkoutByUsername(@PathVariable String username) {
        return service.getWorkoutByUsername(username);
    }

    @PostMapping("api/workouts")
    public Workout saveWorkout(@RequestBody Workout workout) {
        return service.saveWorkout(workout);
    }

}
