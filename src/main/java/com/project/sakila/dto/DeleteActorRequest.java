package com.project.sakila.dto;


import lombok.Data;

@Data
public class DeleteActorRequest {
    private final String firstName;
    private final String lastName;
}
