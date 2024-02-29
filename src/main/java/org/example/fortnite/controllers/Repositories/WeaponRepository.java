package org.example.fortnite.controllers.Repositories;

import org.example.fortnite.models.Weapon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeaponRepository extends CrudRepository<Weapon, Integer> {
    Weapon findByName(@Param("name") String name);
    List<Weapon> findByTyp(@Param("typ") String typ);
}
