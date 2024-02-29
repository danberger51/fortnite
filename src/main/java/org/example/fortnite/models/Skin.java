package org.example.fortnite.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "skin")
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_skin")
    private Integer id;

    private String seltenheit;

    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeltenheit() {
        return seltenheit;
    }

    public void setSeltenheit(String seltenheit) {
        this.seltenheit = seltenheit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
