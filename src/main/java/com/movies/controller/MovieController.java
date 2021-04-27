package com.movies.controller;

import com.movies.exception.NotFoundException;
import com.movies.model.dto.CreateUpdateMovie;
import com.movies.model.node.Movie;
import com.movies.repositories.MovieRepository;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Movie"})
public class MovieController {

	private final ModelMapper modelMapper;
	private final MovieRepository movieRepository;

	@Autowired
	public MovieController(ModelMapper modelMapper, MovieRepository movieRepository){
		this.modelMapper = modelMapper;
		this.movieRepository = movieRepository;
	}

	@GetMapping("movie/{movieId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Movie> getMovie(@PathVariable("movieId") String movieId) throws NotFoundException {
		Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

		return new ResponseEntity(movie, HttpStatus.OK);
	}

	@PostMapping("movie")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Movie> postMovie(@RequestBody @Valid CreateUpdateMovie createUpdateMovie) {
		Movie movie = this.modelMapper.map(createUpdateMovie, Movie.class);

		this.movieRepository.save(movie);

		return new ResponseEntity(movie, HttpStatus.CREATED);
	}
}


