package app.fitness.Workout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workout {

    private Long id;
    @NonNull
    private String workoutType;
    @NonNull
    private String date;

    private List<Exercise> exercises;
}
