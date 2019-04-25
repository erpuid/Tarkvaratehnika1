package app.fitness.services;


import app.fitness.entities.PlanExercise;
import app.fitness.entities.PlanWorkout;
import app.fitness.entities.WorkoutPlan;
import app.fitness.repositories.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkoutPlanService {

    @Autowired
    WorkoutPlanRepository planRepository;

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
        return planRepository.save(workoutPlan);
    }
}
