package app.fitness.controllers;


import app.fitness.entities.WorkoutPlan;
import app.fitness.services.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkoutPlanController {



    @Autowired
    private WorkoutPlanService service;

    @GetMapping("api/plan")
    public List<WorkoutPlan> getAllWorkoutPlans() {
        return service.getAllWorkoutPlans();
    }

    @PostMapping("api/plan")
    public WorkoutPlan saveWorkoutPlan(@RequestBody WorkoutPlan workoutPlan) {
        return service.saveWorkoutPlan(workoutPlan);
    }

}
