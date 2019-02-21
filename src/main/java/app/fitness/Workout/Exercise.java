package app.fitness.Workout;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {

    @NonNull
    private String exerciseName;
    private int sets;
    private int repetitions;
    private int weight;
}
