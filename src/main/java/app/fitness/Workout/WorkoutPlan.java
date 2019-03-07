package app.fitness.Workout;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.jdbc.Work;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Data
@Setter
public class WorkoutPlan {

    private String planName;
    private List<Workout> workouts;

}
