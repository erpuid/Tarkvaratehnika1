package app.fitness;

import app.fitness.config.CustomUserDetails;
import app.fitness.dao.UserRepository;
import app.fitness.entities.Role;
import app.fitness.entities.User;
import app.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.Arrays;

@SpringBootApplication
public class FitnessApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(FitnessApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:hsqldb:mem:my-database");

        DatabasePopulatorUtils.execute(dataBasePopulator(), ds);
        return ds;
    }

    private ResourceDatabasePopulator dataBasePopulator() {
        return new ResourceDatabasePopulator(new ClassPathResource("schema.sql"),
                new ClassPathResource("data.sql"));
    }

    @Bean
    public JdbcTemplate getTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository, UserService userService) throws Exception {
        if (repository.count()==0)
            userService.save(new User("admin", "adminPassword", Arrays.asList(new Role("USER"), new Role("ADMIN"))));
        builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
    }

    /**
     * We return an istance of our CustomUserDetails.
     * @param repository
     * @return
     */
    private UserDetailsService userDetailsService(final UserRepository repository) {
        return username -> new CustomUserDetails(repository.findByUsername(username));
    }
}
}
