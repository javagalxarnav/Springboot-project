package com.project.sakila.dto;


import com.project.sakila.entities.Film;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PartialFilmResponse {
    private final Short id;
    private final String title;
    private final Integer releaseYear;
    private final List<ActorNameResponse> actors;

    public PartialFilmResponse(Film film){
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
        this.actors = film.getCast().stream()
                .map(ActorNameResponse::new)
                .collect(Collectors.toList());


    }


}
