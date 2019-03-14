package app.fitness.services;

import app.fitness.Entities.PlanExercise;
import app.fitness.Entities.PlanWorkout;
import app.fitness.Entities.WorkoutPlan;
import app.fitness.repositories.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkoutPlanService {

    @Autowired
    WorkoutPlanRepository planRepository;

    @GetMapping("api/plan")
    public List<WorkoutPlan> getAllWorkoutPlans() {
        return planRepository.findAll();
    }

    @PostMapping("api/plan")
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
