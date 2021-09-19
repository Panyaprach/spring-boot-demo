package com.example.demo.movie;

import com.example.demo.exception.ImmutableChangeException;
import com.example.demo.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(MovieController.PATH)
public class MovieController {
    public final static String PATH = "/movies";

    @Autowired
    MovieService service;

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
        Movie created = service.create(movie);

        URI location = URI.create(String.format(PATH + "/%s", created.getId()));

        return ResponseEntity.created(location)
                .body(created);
    }

    @GetMapping
    public ResponseEntity<?> getMovies(MovieCriteria criteria) {
        List<Movie> movies = service.findAll(criteria);

        return ResponseEntity.ok(movies);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getMovieById(@PathVariable("id") String id) {
        Movie movie = service.findById(id);

        return ResponseEntity.ok(movie);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMovie(@PathVariable("id") String id, @RequestBody Movie movie) {
        if (!movie.getId().equals(id)) {
            throw new ImmutableChangeException("'id' property cannot change!");
        }

        movie = service.update(movie);

        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable("id") String id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
