package com.movies.controller;

import com.movies.exception.NotFoundException;
import com.movies.model.dto.Cast;
import com.movies.model.dto.CreateUpdateMovie;
import com.movies.model.dto.Crew;
import com.movies.model.node.Movie;
import com.movies.model.dto.MovieWithRating;
import com.movies.repositories.MovieRepository;
import com.movies.service.MovieService;
import com.movies.setup.SetupBeans;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Api(tags = { SetupBeans.MOVIE })
public class MovieController {

	private final ModelMapper modelMapper;
	private final MovieService movieService;

	@Autowired
	public MovieController(ModelMapper modelMapper,
						   MovieService movieService){
		this.modelMapper = modelMapper;
		this.movieService = movieService;
	}

	@Timed(
			value="movies.get",
			histogram = true,
			percentiles = {0.95,0.99}
	)
	@GetMapping("movies")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<MovieWithRating>> getMovies() throws NotFoundException {
		Collection<MovieWithRating> movies = this.movieService.findAllWithRating();

		if(movies.isEmpty()){
			throw new NotFoundException("No movies found!");
		}

		return new ResponseEntity(movies, HttpStatus.OK);
	}

	@Timed(
			value="movie.create",
			histogram = true,
			percentiles = {0.95,0.99}
	)
	@PostMapping("movie")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Movie> postMovie(@RequestBody @Valid CreateUpdateMovie createUpdateMovie) {
		Movie movie = this.movieService.movieRepository().save(this.modelMapper.map(createUpdateMovie, Movie.class));

		return new ResponseEntity(movie, HttpStatus.CREATED);
	}

	@Timed(
			value="movie.get",
			histogram = true,
			percentiles = {0.95,0.99}
	)
	@GetMapping("movie/{movieId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MovieWithRating> getMovie(@PathVariable("movieId") String movieId) throws NotFoundException {
		MovieWithRating movie = this.movieService.findByIdWithRating(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

		return new ResponseEntity(movie, HttpStatus.OK);
	}

	@Timed(
			value="movies.cast.get",
			histogram = true,
			percentiles = {0.95,0.99}
	)
	@GetMapping("movies/{movieId}/cast")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Cast>> getCast(@PathVariable("movieId") String movieId) throws NotFoundException {
		Collection<Cast> cast = this.movieService.findCast(movieId);

		if(cast.isEmpty()){
			throw new NotFoundException("No cast members found for movie!");
		}

		return new ResponseEntity(cast, HttpStatus.OK);
	}

	@Timed(
			value="movies.crew.get",
			histogram = true,
			percentiles = {0.95,0.99}
	)
	@GetMapping("movies/{movieId}/crew")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Crew>> getCrew(@PathVariable("movieId") String movieId) throws NotFoundException {
		Collection<Crew> crew = this.movieService.findCrew(movieId);

		if(crew.isEmpty()){
			throw new NotFoundException("No crew members found for movie!");
		}

		return new ResponseEntity(crew, HttpStatus.OK);
	}
}
