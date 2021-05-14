package com.movies;

import com.movies.model.node.Movie;
import com.movies.model.dto.MovieWithRating;
import com.movies.model.node.Person;
import com.movies.repositories.MovieRepository;
import com.movies.repositories.PersonRepository;
import com.movies.service.MovieService;
import lombok.Value;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Value
public class Mocks {
    MovieRepository movieRepository;
    MovieService movieService;
    PersonRepository personRepository;
    ModelMapper modelMapper;

    public Mocks() {
        this.movieRepository = mock(MovieRepository.class);
        this.movieService = mock(MovieService.class);

        when(this.movieService.movieRepository()).thenReturn(this.movieRepository);

        this.personRepository = mock(PersonRepository.class);
        this.modelMapper = mock(ModelMapper.class);
    }

    public void findMovieById(String id, String title) {
        if(!id.isEmpty() && !title.isEmpty()) {
            when(this.movieRepository.findById(any())).thenAnswer(i -> {
                Movie m = new Movie();
                m.setMovieId(id);
                m.setTitle(title);

                return Optional.of(m);
            });
        }else {
            when(this.movieRepository.findById(any())).thenReturn(Optional.empty());
        }
    }

    public void findMovieByIdWithRating(String id, String title, Double rating, Integer reviews) {
        if(!id.isEmpty() && !title.isEmpty()) {
            when(this.movieService.findByIdWithRating(any())).thenAnswer(i -> {
                MovieWithRating m = MovieWithRating.builder()
                        .movieId(id)
                        .title(title)
                        .rating(rating)
                        .reviews(reviews)
                        .build();

                return Optional.of(m);
            });
        }else {
            when(this.movieService.findByIdWithRating(any())).thenReturn(Optional.empty());
        }
    }

//    public void findAllMovieByIdWithRating(String id, String title, Double rating, Integer reviews) {
//        if(!id.isEmpty() && !title.isEmpty()) {
//            when(this.movieRepository.findAllByIdWithRating(any())).thenAnswer(i -> {
//                MovieRating m = new MovieRating();
//                m.setMovieId(id);
//                m.setTitle(title);
//                m.setRating(rating);
//                m.setReviews(reviews);
//
//                return Optional.of(m);
//            });
//        }else {
//            when(this.movieRepository.findAllByIdWithRating(any())).thenReturn(Optional.empty());
//        }
//    }

    public void saveMovie(String id, String title) {
        when(this.movieRepository.save(any())).thenAnswer(i -> {
            Movie m = new Movie();
            m.setMovieId(id);
            m.setTitle(title);

            return m;
        });
    }

    public void findPersonById(String id, String firstName, String lastName) {
        if(!id.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()) {
            when(this.personRepository.findById(any())).thenAnswer(i -> {
                Person p = new Person();
                p.setPersonId(id);
                p.setFirstName(firstName);
                p.setLastName(lastName);

                return Optional.of(p);
            });
        }else {
            when(this.personRepository.findById(any())).thenReturn(Optional.empty());
        }
    }

    public void savePerson(String id,  String firstName, String lastName) {
        when(this.personRepository.save(any())).thenAnswer(i -> {
            Person p = new Person();
            p.setPersonId(id);
            p.setFirstName(firstName);
            p.setLastName(lastName);

            return p;
        });
    }
}
