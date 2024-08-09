package com.example.demo.movie;

import com.example.demo.jpa.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MovieMutation {
    @Autowired
    private MovieService service;

    @MutationMapping
    public Movie movie(Movie movie) {

        return service.create(movie);
    }
}
