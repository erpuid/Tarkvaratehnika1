package app.fitness.Entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class PlanWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String workoutName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "planWorkout")
    private List<PlanExercise> planExercises;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="workout_plan_id", nullable=false)
    private WorkoutPlan workoutPlan;

    public PlanWorkout(){}

}
