package org.example.fortnite.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "waffen")
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_waffen")
    private Integer id;

    @NotBlank(message = "Der Typ von der Waffe darf nicht Leer sein.")
    @NotNull(message = "Der Typ von der Waffe muss ausgefühlt sein")
    @Column(name = "typ")
    private String typ;

    @NotBlank(message = "Der Name von der Waffe darf nicht Leer sein.")
    @NotNull(message = "Der Name von der Waffe muss ausgefühlt sein")
    @Column(name = "name")
    private String name;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

}
