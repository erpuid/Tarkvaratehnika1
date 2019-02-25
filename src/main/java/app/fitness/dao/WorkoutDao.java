package app.fitness.dao;

import app.fitness.Workout.Exercise;
import app.fitness.Workout.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;


@Repository
public class WorkoutDao {

    @Autowired
    private JdbcTemplate template;


    public Workout findById(Long id) {
        System.out.println("ID: " + id);
        String sql = "select * from workout where id=?";
        //return new Workout(1L, "Upper", new java.sql.Date(System.currentTimeMillis()));
        return template.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new Workout(
                rs.getLong("id"),
                rs.getString("workoutType"),
                rs.getString("date"),
                getExercises(id)
        ));
    }


    public List<Workout> findAllWorkouts() {
        String sql = "select * from workout order by date desc";
        return template.query(sql, (rs, rowNum) -> new Workout(
                rs.getLong("id"),
                rs.getString("workoutType"),
                rs.getString("date"),
                getExercises(rs.getLong("id"))
        ));
    }

    public Workout saveWorkout(Workout workout) {
        String sql = "insert into workout (id, workouttype, date) values (next value for seq1, ?, ?)";
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        System.out.println(template);
        template.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, workout.getWorkoutType());
            ps.setString(2, workout.getDate());
            return ps;
        }, holder);
        System.out.println("ID: " + holder.getKey().longValue());
        workout.setId(holder.getKey().longValue());
        insertExercises(workout);
        return workout;
    }

    private List<Exercise> getExercises(Long id) {
        String sql = "select * from exercise where id = ?";
        return template.query(sql, new Object[]{id}, (rs, rowNum) -> new Exercise(
                rs.getString("exerciseName"),
                rs.getInt("sets"),
                rs.getInt("repetitions"),
                rs.getInt("weight")
        ));
    }

    private void insertExercises(Workout workout) {
        if (workout.getExercises() != null) {
            String sql = "insert into exercise (id, exercisename, sets, repetitions, weight) values (?, ?, ?, ?, ?)";
            for (Exercise ex:workout.getExercises()) {
                template.update(conn -> {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setLong(1, workout.getId());
                    ps.setString(2, ex.getExerciseName());
                    ps.setInt(3, ex.getSets());
                    ps.setInt(4, ex.getRepetitions());
                    ps.setInt(5, ex.getWeight());
                    return ps;
                });
            }
        }
    }
}
