package com.example.demo.movie;

import com.example.demo.model.Category;
import com.example.demo.model.Movie;
import com.example.demo.model.Movie_;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;

public class MovieSpecification {
    static Specification<Movie> nameContains(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Movie_.NAME), contains(name));
    }

    static Specification<Movie> categoryIs(Category category) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Movie_.CATEGORY), category);
    }

    private static String contains(String str) {
        return MessageFormat.format("%{0}%", str);
    }
}
