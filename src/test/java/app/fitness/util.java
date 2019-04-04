package app.fitness;

import antlr.build.Tool;
import app.fitness.entities.Exercise;
import app.fitness.entities.Workout;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class util {


    static String obtainAccessToken(String username, String password, MockMvc mockMvc) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .with(httpBasic("my-trusted-client","secret"))
                .params(params)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());


        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }


    static Workout createValidWorkout() {
        Workout workout = new Workout();
        workout.setDate("2019-04-03");
        workout.setUserName("user2");
        workout.setWorkoutName("Benchi päev");
        Exercise exercise = new Exercise();
        exercise.setExerciseName("Bench");
        exercise.setRepetitions(6);
        exercise.setSets(3);
        exercise.setWeight(100);
        workout.setExercises(Arrays.asList(exercise));
        return workout;
    }
}


