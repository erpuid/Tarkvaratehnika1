package app.fitness.Workout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
public class Workout {
    private enum workoutTypeEnum{
        CHEST, SHOULDER, ARMS,BACK, LEGS, UPPER, LOWER, PUSH, PULL
    }

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String workoutType ;
    private Date date;

    //private ArrayList<Exercise> exercises;
}
