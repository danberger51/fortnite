package org.example.fortnite.models;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.ReadOnlyProperty;

@SpringBootApplication
@EntityScan("org.example.fortnite.models")
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Integer id;

    @NotBlank(message = "Username darf nicht Leer sein.")
    @NotNull(message = "Username muss ausgefühlt sein")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Passwort darf nicht Leer sein!")
    @NotNull(message = "Passwort muss ausgefühlt sein")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
