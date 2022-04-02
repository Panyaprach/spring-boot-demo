package com.example.demo.movie;

import com.example.demo.model.Category;
import com.example.demo.model.Movie;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieMutation implements GraphQLMutationResolver {
    @Autowired
    private MovieService service;

    public Movie movie(String name, Category category, List<String> tags) {
        Movie movie = Movie.builder()
                .withName(name)
                .withCategory(category)
                .withTags(tags)
                .build();

        return service.create(movie);
    }
}
