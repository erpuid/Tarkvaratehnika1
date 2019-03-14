package app.fitness.Entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String workoutName;
    private String date;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "workout")
    private List<Exercise> exercises = new ArrayList<>();

    public Workout() {}

}
