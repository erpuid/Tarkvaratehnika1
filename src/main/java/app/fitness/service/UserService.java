package app.fitness.service;


import app.fitness.dao.UserRepository;
import app.fitness.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void save(User user) {
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
}
