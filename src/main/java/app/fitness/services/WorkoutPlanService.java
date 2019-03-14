package app.fitness.services;

import app.fitness.Entities.WorkoutPlan;
import app.fitness.repositories.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
