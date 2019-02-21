package app.fitness.controller;


import app.fitness.Workout.Workout;
import app.fitness.dao.WorkoutDao;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FitController {

    @Autowired
    private WorkoutDao dao;

    @GetMapping("api/workouts")
    public Workout getAllWorkouts() {
        return dao.findById(1L);
    }

    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("api/workouts/{id}")
    public Workout findById(@PathVariable Long id) {
        return dao.findById(id);
    }

    @PostMapping("api/workouts")
    public Workout saveWorkout(@RequestBody @Valid Workout workout) {
        System.out.println(workout);
        return dao.saveWorkout(workout);
    }
}
