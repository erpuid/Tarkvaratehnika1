package app.fitness;

import app.fitness.entities.User;
import app.fitness.repositories.ExerciseRepository;
import app.fitness.repositories.UserRepository;
import app.fitness.repositories.WorkoutPlanRepository;
import app.fitness.repositories.WorkoutRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class RepositoryTests {


    @MockBean
    private UserRepository userRepository;

    @MockBean
    private WorkoutRepository workoutRepository;

    @MockBean
    private ExerciseRepository exerciseRepository;

    @MockBean
    private WorkoutPlanRepository planRepository;


    @Test
    public void ContextLoads() {
    }

    @Test
    public void UserRepositoryFindByUsername() {
        User user = new User();
        user.setUsername("testUser");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        User found = userRepository.findByUsername(user.getUsername());

        assertEquals(found.getUsername(), user.getUsername());
    }
}