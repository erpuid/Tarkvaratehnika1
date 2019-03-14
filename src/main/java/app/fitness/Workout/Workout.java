package app.fitness.Workout;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Workout {

    private Long id;
    @NonNull
    private String workoutName;
    @NonNull
    private String date;

    private List<Exercise> exercises;
}
