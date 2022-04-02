package com.example.demo.graphql;

import com.example.demo.model.Movie;
import com.example.demo.movie.MovieService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieResolver implements GraphQLQueryResolver {
    @Autowired
    MovieService service;

    // Signature and name must match graphql query schemas
    public Movie movie(String id) {
        return service.findById(id);
    }
}
