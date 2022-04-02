package com.example.demo.movie;

import com.example.demo.model.Category;
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

    public List<Movie> movies(String name, Category category) {
        MovieCriteria criteria = MovieCriteria.builder()
                .name(name)
                .category(category)
                .build();
        List<Movie> movies = service.findAll(criteria);
        return movies;
    }
}
