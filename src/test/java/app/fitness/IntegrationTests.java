package app.fitness;


import app.fitness.entities.Workout;
import app.fitness.entities.WorkoutPlan;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static app.fitness.util.createValidWorkout;
import static app.fitness.util.createWorkoutPlan;
import static app.fitness.util.obtainAccessToken;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTests {

    @Autowired
    private MockMvc mvc;

    private static String token;


    @Before
    public void loginTest() throws Exception {
        token = obtainAccessToken("user2", "user", mvc);
        System.out.println("BEFORE: " + token);
        assertNotNull(token);
    }


    @Test
    public void WorkoutServiceGetByUserNameEmpty() throws Exception {
        String json = getRequest("/api/workouts/user2");
        ObjectMapper mapper = new ObjectMapper();
        Workout[] workouts = mapper.readValue(json, Workout[].class);
        assertArrayEquals(workouts, new Workout[0]);
    }

    @Test
    public void WorkoutServicePost() throws Exception {
        Workout workout = createValidWorkout("user2", "Upper");
        ObjectMapper mapper = new ObjectMapper();
        String postJson = mapper.writeValueAsString(workout);
        ResultActions result = postRequest(postJson, "/api/workouts");
        String json = result.andReturn().getResponse().getContentAsString();
        Workout resultWorkout = mapper.readValue(json, Workout.class);
        assertEquals(resultWorkout.getId(), Long.valueOf(1));
    }


    @Test
    public void GetWorkoutPlanController() throws Exception {
        String json = getRequest("/api/plan");
        ObjectMapper mapper = new ObjectMapper();
        int initialLength = mapper.readValue(json, WorkoutPlan[].class).length;
        WorkoutPlan plan = createWorkoutPlan("Test plan");
        String postJson = mapper.writeValueAsString(plan);

        postRequest(postJson, "/api/plan");
        // Get all plans and check that the new one is there
        json = getRequest("/api/plan");
        WorkoutPlan[] plans = mapper.readValue(json, WorkoutPlan[].class);

        // Check that a workoutPlan with name "Test plan" is in the array.
        boolean planNameInArray = false;
        for (WorkoutPlan p: plans) {
            if (p.getPlanName().equals("Test plan")) {
                planNameInArray = true;
            }
        }
        assertEquals(initialLength + 1, plans.length);
        assertTrue(planNameInArray);
    }

    private ResultActions postRequest(String postJson, String url) throws Exception {
        return mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("access_token", token)
                .content(postJson))
                .andExpect(status().isOk());
    }


    private String getRequest(String url) throws Exception {
        ResultActions result = mvc.perform(get(url)
                .param("access_token", token))
                .andExpect(status().isOk());
        return result.andReturn().getResponse().getContentAsString();
    }
}