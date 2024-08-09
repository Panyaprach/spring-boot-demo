package com.example.demo.movie;

import com.example.demo.jpa.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MovieResolver {
    @Autowired
    MovieService service;

    @SchemaMapping
    public Movie movie(@Argument String id) {
        return service.findById(id);
    }

    @SchemaMapping
    public List<Movie> movies(@Argument MovieCriteria criteria) {
        if (criteria == null)
            criteria = new MovieCriteria();

        List<Movie> movies = service.findAll(criteria);

        return movies;
    }
}
