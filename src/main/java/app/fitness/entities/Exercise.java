package app.fitness.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String exerciseName;
    private int sets;
    private int repetitions;
    private int weight;
    //private Long workoutId;

    @JsonIgnore
    @ManyToOne
    //@JoinColumn(name="workout_id", nullable=false)
    @JoinColumn(name="workout_id")
    private Workout workout;

    public Exercise(){}
}
