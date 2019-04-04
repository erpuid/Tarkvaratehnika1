package app.fitness;

import app.fitness.entities.Workout;
import app.fitness.security.AuthorizationServerConfig;
import app.fitness.services.WorkoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.AssertionFailure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static app.fitness.util.createValidWorkout;
import static app.fitness.util.obtainAccessToken;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest()
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    private WebApplicationContext wac;


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void ValidLoginTest() throws Exception {
        obtainAccessToken("user", "user", mockMvc);
    }

    @Test
    public void InvalidPassword() throws Exception {
        try {
            String accessToken = obtainAccessToken("user", "user2", mockMvc);
            fail("Exception not thrown");
        } catch (AssertionError e) {
            System.out.println(e.toString());
        }
    }


    @Test
    public void InvalidUserName() throws Exception {
        try {
            String accessToken = obtainAccessToken("user10", "user", mockMvc);
            fail("Exception not thrown");
        } catch (AssertionError e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void UserHasAccessToWorkouts() throws Exception {
        String accessToken = obtainAccessToken("user", "user", mockMvc);
        System.out.println(accessToken);
        ResultActions result = mockMvc.perform(get("/api/workouts/user")
                .param("access_token", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void UserHasAccessToWorkoutPlans() throws Exception {
        String accessToken = obtainAccessToken("user", "user", mockMvc);
        System.out.println(accessToken);
        ResultActions result = mockMvc.perform(get("/api/plan")
                .param("access_token", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void cantAccessWorkouts() throws Exception {
        Workout workout = createValidWorkout();
        mockMvc.perform(post("/api/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(workout)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void cantAccessWorkoutPlans() throws Exception {
        mockMvc.perform(get("/api/plan")
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void TestRegistrationAndLoginToNewUser() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "testuser");
        params.put("password", "password");
        String json = new ObjectMapper().writeValueAsString(params);
        ResultActions result
                = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
        obtainAccessToken("testuser", "password", mockMvc);
    }


    @Test
    public void TestRegistrationUserNameTaken() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "user");
        params.put("password", "password");
        String json = new ObjectMapper().writeValueAsString(params);
        ResultActions result
                = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
        System.out.println(result.andReturn().getResponse().getContentAsString());
        assertEquals("This username is already taken!", result.andReturn().getResponse().getContentAsString());
    }


    @Test
    public void TestRegistrationInvalidUserNames() throws Exception {
        List<String> invalidUserNames = Arrays.asList(null, "", "asd", ")}(");
        HashMap<String, String> params = new HashMap<>();
        params.put("password", "password");
        for (String username: invalidUserNames) {
            params.put("username", username);
            String json = new ObjectMapper().writeValueAsString(params);
            ResultActions result
                    = mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept("application/json;charset=UTF-8"))
                    .andExpect(status().isOk());
            assertEquals("Enter valid username!", result.andReturn().getResponse().getContentAsString());
        }
    }
}



