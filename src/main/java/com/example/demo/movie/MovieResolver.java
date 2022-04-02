package com.example.demo.movie;

import com.example.demo.model.Movie;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieResolver implements GraphQLQueryResolver {
    @Autowired
    MovieService service;

    // Signature and name must match graphql query schemas
    public Movie movie(String id) {
        return service.findById(id);
    }

    public List<Movie> movies(MovieCriteria criteria) {
        if (criteria == null)
            criteria = new MovieCriteria();

        List<Movie> movies = service.findAll(criteria);

        return movies;
    }
}
