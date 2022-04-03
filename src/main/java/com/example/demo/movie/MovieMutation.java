package com.example.demo.movie;

import com.example.demo.model.Movie;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieMutation implements GraphQLMutationResolver {
    @Autowired
    private MovieService service;

    public Movie movie(Movie movie) {

        return service.create(movie);
    }
}
