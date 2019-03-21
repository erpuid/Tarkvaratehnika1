package app.fitness.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }
}