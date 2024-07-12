Feature: ActorController

  Scenario: An actor is fetched by ID
    Given an actor exists with ID 42
    When a GET request is made for actor with ID 42
    Then an ActorResponse is returned

  Scenario: An non-existent actor is fetched by ID
    Given an actor exists with ID 42
    When a GET request is made for actor with ID 3
    Then a 404 error is thrown

  Scenario: An actor is created
    Given a new actor information with the first name "Jon" and last name "Hill"
    When a POST request is made to create the actor
    Then an actor is successfully created


  Scenario: An actor is updated
    Given an actor exists with ID 7 and the new first name "Arnav" and new last name "Javagal"
    When a PUT request is made to update the actor with ID 7
    Then the actor is successfully updated



  Scenario: An actor is deleted
    Given an actor exists with ID 42
    When a DELETE request is made for actor with ID 42
    Then the actor is successfully deleted

