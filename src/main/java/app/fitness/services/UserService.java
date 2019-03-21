package app.fitness.services;

import app.fitness.entities.User;
import app.fitness.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @GetMapping("/logout/{token}")
    public void logout(@PathVariable String token){
        System.out.println("Token" + token);
        tokenStore.removeAccessToken(tokenStore.readAccessToken(token));
    }

}
