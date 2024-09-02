package com.example.demo.movie;

import com.example.demo.jpa.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MovieQLController {
    @Autowired
    MovieService service;

    @SchemaMapping(typeName = "Query")
    public Movie movie(@Argument String id) {
        return service.findById(id);
    }

    @SchemaMapping(typeName = "Query")
    public List<Movie> movies(@Argument MovieCriteria criteria) {
        if (criteria == null)
            criteria = new MovieCriteria();

        List<Movie> movies = service.findAll(criteria);

        return movies;
    }

    @MutationMapping
    public Movie movie(@Argument MovieInput input) {

        return service.create(input.toMovie());
    }
}
