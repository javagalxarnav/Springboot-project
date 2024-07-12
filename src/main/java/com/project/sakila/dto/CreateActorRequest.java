package com.project.sakila.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateActorRequest {
    private final String firstName;
    private final String lastName;


}

