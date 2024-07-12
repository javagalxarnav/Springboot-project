package com.project.sakila.dto;


import lombok.Data;

@Data
public class UpdateFilmRequest {


    private final String title;
    private final String description;
    private final Integer releaseYear;
    private final Byte languageId;
    private final Byte originalLanguageId;
    private final Float rentalRate;
    private final Short length;
    private final Float replacementCost;
    private final String rating;
}
