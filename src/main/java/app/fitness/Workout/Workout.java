package app.fitness.Workout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workout {

    private Long id;
    @NonNull
    private String workoutType;
    @NonNull
    private String date;

    /*
    public Workout(Date date) {
        this.date = date;
    }

    public Workout(long l, String upper, Date date) {
        this.id = l;
        workoutType = upper;
        this.date = date;
    }
    */
    //private ArrayList<Exercise> exercises;
}
