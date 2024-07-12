package com.project.sakila.dto;

import com.project.sakila.entities.Actor;
import lombok.Getter;

import java.util.List;


@Getter
public class ActorResponse {
    private final Short id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final List<PartialFilmResponse> films;


    public ActorResponse(Actor actor){
        this.id = actor.getId();
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
        this.fullName = firstName + " " + lastName;
        this.films = actor.getFilms()
                .stream()
                .map(PartialFilmResponse::new)
                .toList();
    }
}
