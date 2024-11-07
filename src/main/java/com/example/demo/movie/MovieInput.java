package com.example.demo.movie;

import com.example.demo.jpa.model.Category;
import com.example.demo.jpa.model.Movie;

import java.util.List;

public record MovieInput(String name, Category category, Integer score, List<String> tags) {

    public Movie toMovie() {
        return Movie.builder()
                .withName(name)
                .withCategory(category)
                .withScore(score)
                .withTags(tags)
                .build();
    }
}
