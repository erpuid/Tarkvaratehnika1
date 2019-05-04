package app.fitness.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private WorkoutDifficulty difficulty;
    @Lob
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "workoutPlan")
    private List<PlanWorkout> workouts;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "plans")
    private List<User> users;

    public WorkoutPlan(){}
}
