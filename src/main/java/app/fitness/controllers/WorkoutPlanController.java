package app.fitness.controllers;


import app.fitness.entities.Workout;
import app.fitness.entities.WorkoutPlan;
import app.fitness.services.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("api/plan/favourite")
    public List<WorkoutPlan> getUserFavPlans() {
        return service.getUsersSavedPlans();
    }

    @PostMapping("api/plan/favourite/{planId}")
    public void saveOrDeleteFromFavs(@PathVariable Long planId) {
        System.out.println("CONTROLLERIS planID: " + planId);
        service.saveOrDelPlan(planId);
    }

}
