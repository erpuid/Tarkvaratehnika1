package app.fitness.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }
}