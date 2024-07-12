package com.project.sakila.controllers;


import com.project.sakila.entities.Film;
import com.project.sakila.entities.Language;
import com.project.sakila.repositories.FilmRepository;
import com.project.sakila.dto.CreateFilmRequest;
import com.project.sakila.dto.FilmResponse;
import com.project.sakila.dto.UpdateFilmRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.project.sakila.repositories.LanguageRepository;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    public FilmRepository filmRepository;
    public LanguageRepository languageRepository;

    @GetMapping()
    public List<FilmResponse> realAllFilms(){
        return filmRepository.findAll()
                .stream()
                .map(FilmResponse::new)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public FilmResponse readFilmById(@PathVariable Short id) {
        return filmRepository.findById(id)
                .map(FilmResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public Film createFilm(@RequestBody CreateFilmRequest body){
        final var film = new Film();
        film.setTitle(body.getTitle());
        film.setDescription(body.getDescription());
        film.setReleaseYear(body.getReleaseYear());
        Language language = languageRepository.findById(body.getLanguageId())
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + body.getLanguageId()));
        film.setLanguage(language);
        film.setOriginalLanguageId(body.getOriginalLanguageId());
        film.setRentalRate(body.getRentalRate());
        film.setLength(body.getLength());
        film.setReplacementCost(body.getReplacementCost());
        film.setRating(body.getRating());
        return filmRepository.save(film);
    }



    @PatchMapping("/{id}")
    public Film updateFilm(@PathVariable Short id , @RequestBody UpdateFilmRequest body){

        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(body.getTitle () != null) film.setTitle(body.getTitle());
        if(body.getDescription () != null) film.setDescription(body.getDescription());
        if(body.getReleaseYear() != null) film.setReleaseYear(body.getReleaseYear());
        if(body.getLanguageId () != null){

            Language language = languageRepository.findById(body.getLanguageId())
                    .orElseThrow(() -> new RuntimeException("Language not found with id: " + body.getLanguageId()));

            film.setLanguage(language);
        }

        if(body.getOriginalLanguageId () != null) film.setOriginalLanguageId(body.getOriginalLanguageId());
        if(body.getRentalRate () != null) film.setRentalRate(body.getRentalRate());
        if(body.getLength () != null) film.setLength(body.getLength());
        if(body.getReplacementCost () != null) film.setReplacementCost(body.getReplacementCost());
        if(body.getRating () != null) film.setRating(body.getRating());





        return filmRepository.save(film);

    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Short id) {

        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        filmRepository.delete(film);
    }




}
