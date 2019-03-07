package app.fitness.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
