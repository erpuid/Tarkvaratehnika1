package app.fitness.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;



@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    @JsonIgnore
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles;

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User() {}
}
