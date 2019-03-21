package app.fitness.services;

import app.fitness.entities.Role;
import app.fitness.entities.User;
import app.fitness.entities.UserReg;
import app.fitness.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("getTokenStore")
    @Autowired
    private TokenStore tokenStore;

    @Bean
    private Role userRole() {
        Role role = new Role();
        role.setName("USER");
        return role;
    }

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @GetMapping("/logout/{token}")
    public void logout(@PathVariable String token){
        System.out.println("Token" + token);
        tokenStore.removeAccessToken(tokenStore.readAccessToken(token));
    }

    @GetMapping(value ="/getUsername")
    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @PostMapping("/register")
    public String register(@RequestBody UserReg userReg) {
        System.out.println(userReg.toString());
        Role role = new Role();
        role.setName("USER");
        User user = new User(userReg.getUsername(),
                passwordEncoder.encode(userReg.getPassword()),
                Arrays.asList(userRole()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getUsername() == null || !user.getUsername().matches("[A-Za-z0-9_]+")) {
            return "Enter valid username!";
        }
        if (repository.findByUsername(user.getUsername()) != null) {
            return "This username is already taken";
        }
        repository.save(user);
        return "Success!";

    }

}
