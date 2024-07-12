package com.project.sakila.dto;

import com.project.sakila.entities.Actor;
import lombok.Getter;

@Getter
public class ActorNameResponse {

    private final Short id;
    private final String fullName;

    public ActorNameResponse(Actor actor){
        this.id = actor.getId();
        this.fullName = actor.getFirstName() + " " + actor.getLastName();
    }
}
