package org.example.fortnite.controllers.Repositories;

import org.example.fortnite.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(@Param("username") String username);}//Gibt an welchen wert die abfrage findByUsername in der Datenbank hat.
//@Param("username") gibt den Wert des Parameters mit.

