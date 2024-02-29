package org.example.fortnite.controllers.Repositories;

import org.example.fortnite.models.Skin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SkinRepository extends CrudRepository<Skin, Integer> {
}
