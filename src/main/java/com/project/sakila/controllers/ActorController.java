package com.project.sakila.controllers;

import com.project.sakila.dto.ActorResponse;
import com.project.sakila.dto.CreateActorRequest;
import com.project.sakila.dto.UpdateActorRequest;
import com.project.sakila.entities.Actor;
import com.project.sakila.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/actors")
public class ActorController {


    private final ActorRepository actorRepository;

    @Autowired
    public ActorController(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @GetMapping
    public List<ActorResponse> readAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(ActorResponse::new)
                .toList();

    }

    @GetMapping("/{id}")
    public ActorResponse readActorById(@PathVariable Short id){
        return actorRepository.findById(id)
                .map(ActorResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ActorResponse createActor(@RequestBody CreateActorRequest body){
        final var actor = new Actor();
        actor.setFirstName(body.getFirstName());
        actor.setLastName(body.getLastName());
        final var savedActor = actorRepository.save(actor);
        return new ActorResponse(savedActor);

    }

    @PatchMapping("/{id}")
    public Actor updateActor(@PathVariable Short id , @RequestBody UpdateActorRequest body){

        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(body.getFirstName() != null) actor.setFirstName(body.getFirstName());
        if(body.getLastName() != null) actor.setLastName(body.getLastName());


        return actorRepository.save(actor);

    }

    @DeleteMapping("/{id}")
    public void deleteActor(@PathVariable Short id){

        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        actorRepository.delete(actor);






    }
}
