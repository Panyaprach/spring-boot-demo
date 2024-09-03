package com.example.demo.movie;

import com.example.demo.jpa.model.Category;
import com.example.demo.jpa.model.Movie;
import lombok.Data;

import java.util.List;

@Data
public class MovieInput {
    private String name;
    private Category category;
    private Integer score;
    private List<String> tags;

    public Movie toMovie() {
        return Movie.builder()
                .withName(name)
                .withCategory(category)
                .withScore(score)
                .withTags(tags)
                .build();
    }
}
