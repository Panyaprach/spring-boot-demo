package com.example.demo.movie;

import com.example.demo.jpa.model.Movie;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
public class MovieQLController {
    @Autowired
    MovieService service;

    @Async
    @SchemaMapping(typeName = "Query")
    public CompletableFuture<Movie> movie(@Argument String id) {
        Movie movie = service.findById(id);

        return CompletableFuture.completedFuture(movie);
    }

    @Async
    @SchemaMapping(typeName = "Query")
    public CompletableFuture<List<Movie>> movies(@Argument MovieCriteria criteria,
                                                 @Argument @NotNull @Positive @Valid Integer page,
                                                 @Argument @NotNull @Positive @Valid @Max(100) Integer size
    ) {
        if (criteria == null)
            criteria = new MovieCriteria();

        Pageable paging = PageRequest.of(page - 1, size);
        Page<Movie> movies = service.findAll(criteria, paging);

        return CompletableFuture.completedFuture(movies.toList());
    }

    @Async
    @MutationMapping
    public CompletableFuture<Movie> movie(@Argument MovieInput input) {
        Movie movie = service.create(input.toMovie());

        return CompletableFuture.completedFuture(movie);
    }

    @SubscriptionMapping("newMovie")
    public Flux<Movie> subscribe(@Argument MovieCriteria criteria) {
        if (criteria == null)
            criteria = new MovieCriteria();

        List<Movie> movies = service.findAll(criteria);

        return Flux.fromIterable(movies)
                .delayElements(Duration.ofSeconds(1));
    }
}
