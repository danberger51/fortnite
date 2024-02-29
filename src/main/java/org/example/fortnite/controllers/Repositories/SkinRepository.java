package org.example.fortnite.controllers.Repositories;

import org.example.fortnite.models.Skin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SkinRepository extends CrudRepository<Skin, Integer> {
    List<Skin> findByRarity(@Param("rarity") String rarity);

    Skin findByName(@Param("name") String name);
}
