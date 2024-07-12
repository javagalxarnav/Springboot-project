package com.project.sakila;

import com.project.sakila.controllers.ActorController;
import com.project.sakila.controllers.FilmController;
import com.project.sakila.dto.ActorResponse;
import com.project.sakila.dto.CreateActorRequest;
import com.project.sakila.dto.CreateFilmRequest;
import com.project.sakila.dto.FilmResponse;
import com.project.sakila.dto.UpdateActorRequest;
import com.project.sakila.dto.UpdateFilmRequest;
import com.project.sakila.entities.Actor;
import com.project.sakila.entities.Film;
import com.project.sakila.entities.Language;
import com.project.sakila.repositories.ActorRepository;
import com.project.sakila.repositories.FilmRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import com.project.sakila.repositories.LanguageRepository;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class SakilaApplicationTests {

	private ActorRepository actorRepository;
	private ActorController actorController;
	private FilmRepository filmRepository;
	private FilmController filmController;
	private LanguageRepository languageRepository;

	private static Actor actor1 = new Actor((short)1);
	static {
		actor1.setFirstName("FERGUS");
		actor1.setLastName("BENTLEY");
	}
	private static Actor actor2 = new Actor((short)2);
	static {
		actor2.setFirstName("ARNAV");
		actor2.setLastName("JAVAGAL");
	}



	private static Film film1 = new Film();
	static {

		film1.setTitle("Inception");
		film1.setDescription("A mind-bending thriller");
		film1.setReleaseYear(2010);
	}
	private static Film film2 = new Film();
	static {

		film2.setTitle("The Matrix");
		film2.setDescription("A sci-fi classic");
		film2.setReleaseYear(1999);
	}
	private static final List<Actor> actorStubs = List.of(
			actor1,
			actor2
	);
	private static final List<Film> filmStubs = List.of(
			film1,
			film2
	);

	private static Language language1 = new Language();



	@BeforeEach
	public void init() {
		actorRepository = mock(ActorRepository.class);
		actorController = new ActorController(actorRepository);

		filmRepository = mock(FilmRepository.class);
		languageRepository = mock(LanguageRepository.class);
		filmController = new FilmController();
		filmController.filmRepository = filmRepository;
		filmController.languageRepository = languageRepository;



		doReturn(actorStubs).when(actorRepository).findAll();
		doReturn(Optional.of(actor1)).when(actorRepository).findById((short)1);
		doReturn(Optional.of(actor2)).when(actorRepository).findById((short)2);
		doReturn(Optional.empty()).when(actorRepository).findById((short)3);

		doReturn(filmStubs).when(filmRepository).findAll();
		doReturn(Optional.of(film1)).when(filmRepository).findById((short)1);
		doReturn(Optional.of(film2)).when(filmRepository).findById((short)2);
		doReturn(Optional.empty()).when(filmRepository).findById((short)3);

		doReturn(Optional.of(language1)).when(languageRepository).findById((byte)1);

}
	@Test
	public void actorControllerReturnsActorResponse() {
		final var actual = actorController.readAllActors();
		Assertions.assertEquals(2, actual.size());
		Assertions.assertEquals(actor1.getFirstName(), actual.getFirst().getFirstName());
		Assertions.assertEquals(actor2.getFirstName(), actual.get(1).getFirstName());
	}

	@Test
	public void actorControllerReturnsActorById() {
		final var actual = actorController.readActorById((short)1);
		Assertions.assertEquals(actor1.getId(), actual.getId());
		Assertions.assertEquals(actor1.getFirstName(), actual.getFirstName());
		Assertions.assertEquals(actor1.getLastName(), actual.getLastName());
	}

	@Test
	public void actorControllerThrows404ForInvalidActorId(){
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			actorController.readActorById((short)3);
		});
	}

	@Test
	public void actorControllerCreatesActor(){
		CreateActorRequest request = new CreateActorRequest("Max", "Tom");

		Actor actor = new Actor();
		actor.setFirstName("Max");
		actor.setLastName("Tom");

		when(actorRepository.save(any(Actor.class))).thenReturn(actor);

		ActorResponse response = actorController.createActor(request);

		Assertions.assertEquals("Max", response.getFirstName());
		Assertions.assertEquals("Tom", response.getLastName());


	}

	@Test
	public void actorControllerUpdatesActor() {
		UpdateActorRequest request = new UpdateActorRequest("UpdateFirstName", "UpdatedLastName");


		when(actorRepository.save(any(Actor.class))).thenReturn(actor1);

		Actor response = actorController.updateActor((short) 1, request);

		Assertions.assertEquals("UpdateFirstName",response.getFirstName());
		Assertions.assertEquals("UpdatedLastName", response.getLastName());

	}


	@Test
	public void actorControllerThrows404WhenDeletingNonExistentActor() {
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			actorController.deleteActor((short) 3);
		});
	}
	@Test
	public void actorControllerDeletesActor() {
		doNothing().when(actorRepository).delete(any(Actor.class));

		actorController.deleteActor((short) 1);



		Optional<Actor> deletedActor = actorRepository.findById((short) 3);
		Assertions.assertTrue(deletedActor.isEmpty());
	}

	@Test
	public void filmControllerReturnsFilmResponse() {
		final var actual = filmController.realAllFilms();
		Assertions.assertEquals(2, actual.size());
		Assertions.assertEquals(film1.getTitle(), actual.get(0).getTitle());
		Assertions.assertEquals(film2.getTitle(), actual.get(1).getTitle());
	}


	@Test
	public void filmControllerReturnsFilmById() {
		final var actual = filmController.readFilmById((short)1);
		Assertions.assertEquals(film1.getId(), actual.getId());
		Assertions.assertEquals(film1.getTitle(), actual.getTitle());
		//Assertions.assertEquals(film1.getDescription(), actual.getDescription());
	}


	@Test
	public void filmControllerThrows404ForInvalidFilmId() {
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			filmController.readFilmById((short)3);
		});
	}

	@Test
	public void filmControllerThrows404WhenDeletingNonExistentFilm() {
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			filmController.deleteFilm((short)3);
		});
	}

	@Test
	public void filmControllerDeletesFilm() {
		doNothing().when(filmRepository).delete(any(Film.class));

		filmController.deleteFilm((short)1);

		Optional<Film> deletedFilm = filmRepository.findById((short)3);
		Assertions.assertTrue(deletedFilm.isEmpty());
	}


	@Test
	public void filmControllerCreatesFilm() {

		CreateFilmRequest request = new CreateFilmRequest(
				"Interstellar",
				"A journey through space",
				2014,
				(byte) 1,
				(byte) 1,
				5.99f,
				(short) 169,
				24.99f,
				"PG-13"
		);


		Film film = new Film();
		film.setTitle("Interstellar");
		film.setDescription("A journey through space");
		film.setReleaseYear(2014);
		film.setLanguage(language1);
		film.setOriginalLanguageId((byte)1);
		film.setRentalRate(5.99f);
		film.setLength((short)169);
		film.setReplacementCost(24.99f);
		film.setRating("PG-13");

		when(filmRepository.save(any(Film.class))).thenReturn(film);

		Film response = filmController.createFilm(request);

		Assertions.assertEquals("Interstellar", response.getTitle());
		Assertions.assertEquals("A journey through space", response.getDescription());
		Assertions.assertEquals(2014, response.getReleaseYear());
		Assertions.assertEquals(language1, response.getLanguage());
		Assertions.assertEquals(5.99f, response.getRentalRate());
		//Assertions.assertEquals(169, response.getLength());
		Assertions.assertEquals(24.99f, response.getReplacementCost());
		Assertions.assertEquals("PG-13", response.getRating());
	}

	@Test
	public void filmControllerUpdatesFilm() {
		UpdateFilmRequest request = new UpdateFilmRequest(
				"Inception Updated",
				"A thriller",
				2021,
				(byte) 1,
				(byte) 1,
				6.99f,
				(short) 180,
				29.99f,
				"R"
		);


		when(filmRepository.save(any(Film.class))).thenReturn(film1);

		Film response = filmController.updateFilm((short)1, request);

		Assertions.assertEquals("Inception Updated", response.getTitle());
		Assertions.assertEquals("A thriller", response.getDescription());
		Assertions.assertEquals(2021, response.getReleaseYear());
		Assertions.assertEquals(language1, response.getLanguage());
		Assertions.assertEquals( (byte) 1 , response.getOriginalLanguageId());
		Assertions.assertEquals(6.99f, response.getRentalRate());
		Assertions.assertEquals((short) 180, response.getLength());
		Assertions.assertEquals(29.99f, response.getReplacementCost());
		Assertions.assertEquals("R", response.getRating());
	}





}

