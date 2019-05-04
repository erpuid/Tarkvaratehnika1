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
            System.out.println(planWorkout.getPlanExercises());
            for (PlanExercise exercise:planWorkout.getPlanExercises()) {
                exercise.setPlanWorkout(planWorkout);
            }
        }
        System.out.println("DIFFICULTY: " + workoutPlan.getDifficulty());
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
        boolean deleted = false;
        System.out.println("USERID SAVED: " + selected.getUsers());
        for (User user: selected.getUsers()) {
            System.out.println(user.getUsername() + "LoggedIn: " + loggedIn);
            if (user.getUsername().equals(loggedIn)) {
                selected.getUsers().remove(user);
                user.getPlans().remove(selected);
                deleted = true;
                System.out.println("DELETED!!! plan");
                break;
            }
        }
        if (!deleted) {
            System.out.println("SAVED!!! plan");
            User user = userService.getUserByUserName(loggedIn);
            selected.getUsers().add(user);
            System.out.println("EROORI");
            user.getPlans().add(selected);
        }
        System.out.println("Peale save: " + planRepository.getOne(planId).getUsers());
    }
}
