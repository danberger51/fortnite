package org.example.fortnite.controllers.Repositories;

import org.example.fortnite.models.Weapon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WeaponRepository extends CrudRepository<Weapon, Integer> {
}
