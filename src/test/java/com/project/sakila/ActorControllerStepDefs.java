package com.project.sakila;

import com.project.sakila.controllers.ActorController;
import com.project.sakila.dto.ActorResponse;
import com.project.sakila.dto.CreateActorRequest;
import com.project.sakila.entities.Actor;
import com.project.sakila.repositories.ActorRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.project.sakila.dto.UpdateActorRequest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ActorControllerStepDefs {

    private ActorRepository actorRepository;
    private ActorController actorController;
    private Actor newActor;
    private CreateActorRequest createActorRequest;
    private UpdateActorRequest updateActorRequest;




    private Exception exception;
    private ActorResponse actualActorResponse;
    private Actor existingActor;
    private Actor actualActor;

    @Before
    public void setupMocks() {
        actorRepository = mock(ActorRepository.class);
        actorController = new ActorController(actorRepository);
    }

    @Given("an actor exists with ID {short}")
    public void givenActorExistsWithId(Short id) {
        final var actor = new Actor(id);
        doReturn(Optional.of(actor)).when(actorRepository).findById(id);
    }

    @When("a GET request is made for actor with ID {short}")
    public void whenGetRequestWithId(Short id) {
        try {
            actualActorResponse = actorController.readActorById(id);
            exception = null;
        } catch (Exception e) {
            actualActorResponse = null;
            exception = e;
        }
    }

    @Then("an ActorResponse is returned")
    public void thenActorResponseReturned() {
        Assertions.assertNotNull(actualActorResponse);
    }

    @Then("a {int} error is thrown")
    public void thenAStatusErrorIsThrown(Integer expectedStatusCode) {
        Assertions.assertNotNull(exception);
        Assertions.assertInstanceOf(ResponseStatusException.class, exception);
        final var actualStatusCode = ((ResponseStatusException)exception).getStatusCode().value();
        Assertions.assertEquals(expectedStatusCode, actualStatusCode);
    }


    @Given("a new actor information with the first name {string} and last name {string}")
    public void givenNewActorInfo(String firstName, String lastName) {

        createActorRequest = new CreateActorRequest(firstName,lastName);


        newActor = new Actor();
        newActor.setFirstName(firstName);
        newActor.setLastName(lastName);
        doReturn(newActor).when(actorRepository).save(any(Actor.class));
    }


    @When("a POST request is made to create the actor")
    public void whenPostRequestToCreateActor() {
        try {
            actualActorResponse = actorController.createActor(createActorRequest);

            exception = null;
        } catch (Exception e) {
            actualActorResponse = null;

            exception = e;
        }
    }

    @Then("an actor is successfully created")
    public void thenActorIsSuccessfullyCreated() {
        Assertions.assertNotNull(actualActorResponse);
        Assertions.assertEquals(newActor.getFirstName(), actualActorResponse.getFirstName());
        Assertions.assertEquals(newActor.getLastName(), actualActorResponse.getLastName());
    }




    @Given("an actor exists with ID {short} and the new first name {string} and new last name {string}")
    public void givenActorExistsWithIdAndNewName(Short id, String newFirstName, String newLastName) {
        existingActor = new Actor(id);
        doReturn(Optional.of(existingActor)).when(actorRepository).findById(id);

        updateActorRequest = new UpdateActorRequest(newFirstName, newLastName);
        existingActor.setFirstName(newFirstName);
        existingActor.setLastName(newLastName);
        doReturn(existingActor).when(actorRepository).save(any(Actor.class));
    }


    @When("a PUT request is made to update the actor with ID {short}")
    public void whenPutRequestToUpdateActor(Short id) {
        try {
            actualActor = actorController.updateActor(id, updateActorRequest);
            exception = null;
        } catch (Exception e) {
            actualActor = null;
            exception = e;
        }
    }


    @Then("the actor is successfully updated")
    public void thenActorIsSuccessfullyUpdated() {
        Assertions.assertNotNull(actualActor);
        Assertions.assertEquals(updateActorRequest.getFirstName(), actualActor.getFirstName());
        Assertions.assertEquals(updateActorRequest.getLastName(), actualActor.getLastName());
    }



    @When("a DELETE request is made for actor with ID {short}")
    public void whenDeleteRequestWithId(Short id) {
        try {
            doNothing().when(actorRepository).deleteById(id);
            actorController.deleteActor(id);
            exception = null;
        } catch (Exception e) {
            exception = e;
        }
    }



    @Then("the actor is successfully deleted")
    public void thenActorIsSuccessfullyDeleted() {
        Assertions.assertNull(exception);
    }



}

