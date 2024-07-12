package com.project.sakila.repositories;

import com.project.sakila.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Short> {

}
