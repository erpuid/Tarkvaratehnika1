package app.fitness.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String planName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "workoutPlan")
    private List<PlanWorkout> workouts;

    public WorkoutPlan(){}
}
