package app.fitness.services;


import app.fitness.entities.*;
import app.fitness.repositories.UserRepository;
import app.fitness.repositories.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkoutPlanService {

    @Autowired
    WorkoutPlanRepository planRepository;

    @Autowired
    UserService userService;

    public List<WorkoutPlan> getAllWorkoutPlans() {
        return planRepository.findAll();
    }

    public WorkoutPlan saveWorkoutPlan(@RequestBody WorkoutPlan workoutPlan) {
        for (PlanWorkout planWorkout:workoutPlan.getWorkouts()) {
            planWorkout.setWorkoutPlan(workoutPlan);
            for (PlanExercise exercise:planWorkout.getPlanExercises()) {
                exercise.setPlanWorkout(planWorkout);
            }
        }
        return planRepository.save(workoutPlan);
    }

    public List<WorkoutPlan> getUsersSavedPlans() {
        List<WorkoutPlan> favePlans = new ArrayList<>();
        String loggedIn = SecurityContextHolder.getContext().getAuthentication().getName();
        for(WorkoutPlan plan: getAllWorkoutPlans()) {
            for (User user:plan.getUsers()) {
                if (loggedIn.equals(user.getUsername())) {
                    favePlans.add(plan);
                }
            }
        }
        return favePlans;
    }

    public void saveOrDelPlan(Long planId) {
        WorkoutPlan selected = planRepository.getOne(planId);
        String loggedIn = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.getUserByUserName(loggedIn);
        boolean deleted = false;
        for (User user: selected.getUsers()) {
            if (user.getUsername().equals(loggedIn)) {
                selected.getUsers().remove(user);
                loggedInUser.getPlans().remove(selected);
                deleted = true;
                break;
            }
        }
        if (!deleted) {
            selected.getUsers().add(loggedInUser);
            loggedInUser.getPlans().add(selected);
        }
        planRepository.save(selected);
        userService.saveExisting(loggedInUser);
    }
}
