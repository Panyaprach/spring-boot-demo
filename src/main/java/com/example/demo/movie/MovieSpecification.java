package com.example.demo.movie;

import com.example.demo.model.Category;
import com.example.demo.model.Movie;
import com.example.demo.model.Movie_;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;

public class MovieSpecification {
    static Specification<Movie> nameContains(String name) {
        if (name == null) {
            return null;
        }

        return (root, cq, cb) -> cb.like(root.get(Movie_.NAME), contains(name));
    }

    static Specification<Movie> categoryIs(Category category) {
        if (category == null) {
            return null;
        }

        return (root, cq, cb) -> cb.equal(root.get(Movie_.CATEGORY), category);
    }

    private static String contains(String str) {
        return MessageFormat.format("%{0}%", str);
    }
}
