package com.project.sakila.repositories;


import com.project.sakila.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Short>{
}
