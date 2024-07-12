package com.project.sakila.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "film")
@Getter
@Setter
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    @Setter(AccessLevel.NONE)
    private Short id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Setter
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "original_language_id")
    private Byte originalLanguageId;

    @Column(name = "rental_rate")
    private Float rentalRate;

    @Column(name = "length")
    private Short length;

    @Column(name = "replacement_cost")
    private Float replacementCost;

    @Column(name = "rating")
    private String rating;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = { @JoinColumn(name = "film_id")},
            inverseJoinColumns = { @JoinColumn(name = "actor_id")}

    )
    private List<Actor> cast = new ArrayList<>();


}
