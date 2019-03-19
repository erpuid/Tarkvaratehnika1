package app.fitness.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PlanExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String exerciseName;
    private int sets;
    private int repetitions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="plan_workout_id", nullable=false)
    private PlanWorkout planWorkout;

    public PlanExercise(){}
}
