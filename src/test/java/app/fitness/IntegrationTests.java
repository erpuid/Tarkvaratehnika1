package app.fitness;

import app.fitness.entities.PlanExercise;
import app.fitness.entities.PlanWorkout;
import app.fitness.entities.Workout;
import app.fitness.entities.WorkoutPlan;
import app.fitness.repositories.WorkoutRepository;
import org.aspectj.weaver.tools.Trace;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static app.fitness.util.createWorkoutPlan;
import static app.fitness.util.obtainAccessToken;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void WorkoutServiceGetByUserName() throws Exception {
        String token = obtainAccessToken("user2", "user", mvc);
        Workout workout = new Workout();
        workout.setWorkoutName("Test workout");
        workout.setUserName("user2");
        // Check that user has no workouts inserted
        ResultActions result = mvc.perform(get("/api/workouts/user2")
                .param("access_token", token))
                .andExpect(status().isOk());
        String json = result.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Workout[] workouts = mapper.readValue(json, Workout[].class);
        assertArrayEquals(workouts, new Workout[0]);

        // Post new workout to user.
        String postJson = mapper.writeValueAsString(workout);
        mvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .param("access_token", token)
            .content(postJson))
            .andExpect(status().isOk());
        // Get all workouts and check that the new one is there
        result = mvc.perform(get("/api/workouts/user2")
                .param("access_token", token))
                .andExpect(status().isOk());
        json = result.andReturn().getResponse().getContentAsString();
        workouts = mapper.readValue(json, Workout[].class);
        assertEquals(workouts[0].getWorkoutName(),"Test workout");
    }

    @Test
    public void PostAndGetNewWorkoutPlan() throws Exception {
        String token = obtainAccessToken("user2", "user", mvc);
        WorkoutPlan plan = createWorkoutPlan("Test plan");


        // Check that user has no plans inserted
        ResultActions result = mvc.perform(get("/api/plan")
                .param("access_token", token))
                .andExpect(status().isOk());
        String json = result.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        WorkoutPlan[] plans = mapper.readValue(json, WorkoutPlan[].class);
        int initialLength = plans.length;
        System.out.println("INITIAL LENGTH: " + initialLength);
        // Post new plan.
        String postJson = mapper.writeValueAsString(plan);
        System.out.println("postJason");
        mvc.perform(post("/api/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .param("access_token", token)
                .content(postJson))
                .andExpect(status().isOk());
        // Get all plans and check that the new one is there
        result = mvc.perform(get("/api/plan")
                .param("access_token", token))
                .andExpect(status().isOk());
        json = result.andReturn().getResponse().getContentAsString();
        plans = mapper.readValue(json, WorkoutPlan[].class);

        // Check that a workoutPlan with name "Test plan" is in the array.
        boolean planNameInArray = false;
        for (WorkoutPlan p: plans) {
            if (p.getPlanName().equals("Test plan")) {
                planNameInArray = true;
            }
        }
        // Check that the new a new plan was added and 1 more object is in database
        assertEquals(initialLength + 1, plans.length);
        // Check that there is and
        assertTrue(planNameInArray);
    }
}