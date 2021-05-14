package com.movies.service;

import com.movies.model.dto.Cast;
import com.movies.model.dto.Crew;
import com.movies.model.dto.MovieWithRating;
import com.movies.repositories.MovieRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Log4j2
@Service
public class MovieService {

    private final Neo4jClient neo4jClient;
    private final MovieRepository movieRepository;

    MovieService(Neo4jClient neo4jClient, MovieRepository movieRepository) {
        this.neo4jClient = neo4jClient;
        this.movieRepository = movieRepository;
    }

    public MovieRepository movieRepository() {
        return this.movieRepository;
    }

    public Collection<MovieWithRating> findAllWithRating() {
        return this.neo4jClient
                .query("MATCH (m:Movie) " +
                        "OPTIONAL MATCH (m)<-[r]-(p:Person)  " +
                        "RETURN m, " +
                        "       avg(r.rating) as rating, " +
                        "       count(r.rating) as reviews ")
                .fetchAs(MovieWithRating.class)
                .mappedBy((typeSystem, record) -> {
                    double rating = 0.0;
                    if(!record.get("rating").isNull()){
                        rating = record.get("rating").asDouble();
                    }

                    return MovieWithRating.builder()
                            .movieId(record.get("m").get("movieId").asString())
                            .title(record.get("m").get("title").asString())
                            .rating(rating)
                            .reviews(record.get("reviews").asInt())
                            .build();
                })
                .all();
    }

    public Optional<MovieWithRating> findByIdWithRating(String id) {
        return this.neo4jClient
                .query("MATCH (m:Movie) " +
                        "WHERE m.movieId = $id " +
                        "OPTIONAL MATCH (m:Movie)<-[r]-(p:Person)  " +
                        "RETURN m, " +
                        "       avg(r.rating) as rating, " +
                        "       count(r.rating) as reviews")
                .bind(id).to("id")
                .fetchAs(MovieWithRating.class)
                .mappedBy((typeSystem, record) -> {
                    double rating = 0.0;
                    if(!record.get("rating").isNull()){
                        rating = record.get("rating").asDouble();
                    }

                    return MovieWithRating.builder()
                            .movieId(record.get("m").get("movieId").asString())
                            .title(record.get("m").get("title").asString())
                            .rating(rating)
                            .reviews(record.get("reviews").asInt())
                            .build();
                })
                .one();
    }

    public Collection<Cast> findCast(String id) {
        return this.neo4jClient
                .query("MATCH (m)<-[r]-(p:Person) " +
                        "WHERE m.movieId = $id " +
                        "AND TYPE(r) = 'ACTED_IN' " +
                        "RETURN p, r.character as character")
                .bind(id).to("id")
                .fetchAs(Cast.class)
                .mappedBy((typeSystem, record) -> {
                    return Cast.builder()
                            .firstName(record.get("p").get("firstName").asString())
                            .lastName(record.get("p").get("lastName").asString())
                            .character(record.get("character").asString())
                            .build();
                })
                .all();
    }

    public Collection<Crew> findCrew(String id) {
        return this.neo4jClient
                .query("MATCH (m)<-[r]-(p:Person) " +
                        "WHERE m.movieId = $id " +
                        "AND TYPE(r) in ['DIRECTED','WROTE','PRODUCED'] " +
                        "RETURN p, TYPE(r) as role")
                .bind(id).to("id")
                .fetchAs(Crew.class)
                .mappedBy((typeSystem, record) -> {
                    return Crew.builder()
                            .firstName(record.get("p").get("firstName").asString())
                            .lastName(record.get("p").get("lastName").asString())
                            .role(record.get("role").asString())
                            .build();
                })
                .all();
    }
}
