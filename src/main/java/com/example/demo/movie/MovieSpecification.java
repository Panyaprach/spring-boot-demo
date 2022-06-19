package com.example.demo.movie;

import com.example.demo.jpa.model.Category;
import com.example.demo.jpa.model.Movie;
import com.example.demo.jpa.model.Movie_;
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

    static Specification<Movie> tagsContains(String tag) {
        if (tag == null) {
            return null;
        }

        return (root, cq, cb) -> cb.isMember(tag, root.get(Movie_.TAGS));
    }

    private static String contains(String str) {
        return MessageFormat.format("%{0}%", str);
    }
}
