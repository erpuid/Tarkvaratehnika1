package app.fitness.dao;

import app.fitness.Workout.Workout;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WorkoutDao {

    @Autowired
    private JdbcTemplate template;


    public Workout findById(Long id) {
        String sql = "select * from workout where id=?";
        return template.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new Workout(
                rs.getLong("id"),
                rs.getString("workoutType"),
                rs.getDate("date")
        ));
    }
}
