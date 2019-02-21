package app.fitness.dao;

import app.fitness.Workout.Workout;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                rs.getString("date")
        ));
    }

    public List <Workout> findAll() {
        //Workout workout = new Workout(1L, "Chest", new java.sql.Date(System.currentTimeMillis()));
        return new ArrayList<>();
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
        // TODO Lisa harjutused, mis tegid.
        return workout;
    }
}
