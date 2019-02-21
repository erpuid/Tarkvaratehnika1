package app.fitness.controller;


import app.fitness.Workout.Workout;
import app.fitness.dao.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FitController {

    @Autowired
    private WorkoutDao dao;

    @GetMapping("api/workouts")
    public Workout getAllWorkouts() {
        return dao.findById(1L);
    }
}
