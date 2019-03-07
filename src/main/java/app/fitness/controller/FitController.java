package app.fitness.controller;


import app.fitness.Workout.Workout;
import app.fitness.Workout.WorkoutPlan;
import app.fitness.dao.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FitController {

    @Autowired
    private WorkoutDao dao;

    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("api/workouts")
    public List<Workout> getAllWorkouts() {
        return dao.findAllWorkouts();
    }

    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("api/workouts/{id}")
    public Workout findById(@PathVariable Long id) {
        return dao.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:9000")
    @PostMapping("api/workouts")
    public Workout saveWorkout(@RequestBody Workout workout) {
        System.out.println(workout);
        return dao.saveWorkout(workout);
    }


    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("api/workoutplan")
    public List<WorkoutPlan> getAllWorkoutPlans() {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setPlanName("Test Plan");
        List<Workout> workouts = new ArrayList<>();
        workouts.add(this.findById(1L));
        workouts.add(this.findById(2L));
        plan.setWorkouts(workouts);
        return Arrays.asList(plan);
    }
}
