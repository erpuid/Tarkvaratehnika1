package app.fitness;

import app.fitness.entities.User;
import app.fitness.entities.Workout;
import app.fitness.entities.WorkoutPlan;
import app.fitness.repositories.UserRepository;
import app.fitness.repositories.WorkoutPlanRepository;
import app.fitness.repositories.WorkoutRepository;
import app.fitness.services.WorkoutPlanService;
import app.fitness.services.WorkoutService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static app.fitness.util.createValidWorkout;
import static app.fitness.util.createWorkoutPlan;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@WebAppConfiguration
@AutoConfigureMockMvc
public class UnitTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private WorkoutRepository workoutRepository;

    @MockBean
    private WorkoutPlanRepository planRepository;

    @MockBean
    private WorkoutService workoutService;

    @MockBean
    private WorkoutPlanService planService;

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

    @Test
    public void PlanRepositoryFindAll() {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setPlanName("Test plan");
        Mockito.when(planRepository.findAll()).thenReturn(Arrays.asList(plan));

        List<WorkoutPlan> plans = planRepository.findAll();

        assertEquals(plans.get(0).getPlanName(), "Test plan");
    }

    @Test
    public void WorkoutRepositoryFindAll() {
        Workout workout = new Workout();
        workout.setWorkoutName("Test workout");
        Mockito.when(workoutRepository.findAll()).thenReturn(Arrays.asList(workout));

        List<Workout> plans = workoutRepository.findAll();

        assertEquals(plans.get(0).getWorkoutName(), "Test workout");
    }

    @Test
    public void WorkoutServiceSave() {
        Workout workout = createValidWorkout("user", "Test workout");
        Mockito.when(workoutService.saveWorkout(workout)).thenReturn(workout);
        Workout returned = workoutService.saveWorkout(workout);
        assertThat(returned).isEqualToComparingFieldByField(workout);
    }

    @Test
    public void WorkoutServiceGetAll() {
        Workout workout = createValidWorkout("user", "Test workout");
        Mockito.when(workoutService.getAllWorkouts()).thenReturn(Arrays.asList(workout));
        List<Workout> returned = workoutService.getAllWorkouts();
        Workout returnedWorkout = returned.get(0);
        assertThat(returnedWorkout).isEqualToComparingFieldByField(workout);
    }


    @Test
    public void WorkoutServiceGetByUsername() {
        Workout workout = createValidWorkout("user", "Test workout");
        Mockito.when(workoutService.getWorkoutByUsername("user")).thenReturn(Arrays.asList(workout));
        List<Workout> returned = workoutService.getWorkoutByUsername("user");
        Workout returnedWorkout = returned.get(0);
        assertThat(returnedWorkout).isEqualToComparingFieldByField(workout);
    }

    @Test
    public void WorkoutPlanSave() {
        WorkoutPlan plan = createWorkoutPlan("Test plan");
        Mockito.when(planService.saveWorkoutPlan(plan)).thenReturn(plan);
        WorkoutPlan returned = planService.saveWorkoutPlan(plan);
        assertThat(returned).isEqualToComparingFieldByField(plan);
    }

    @Test
    public void WorkoutPlanGetAllPlans() {
        WorkoutPlan plan = createWorkoutPlan("Test plan");
        Mockito.when(planService.getAllWorkoutPlans()).thenReturn(Arrays.asList(plan));
        List<WorkoutPlan> returned = planService.getAllWorkoutPlans();
        assertThat(returned.get(0)).isEqualToComparingFieldByField(plan);
    }
}