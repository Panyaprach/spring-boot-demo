package com.example.demo.movie;

import com.example.demo.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    Movie create(Movie movie);
    Movie findById(String id);
    List<Movie> findAll(MovieCriteria criteria);
    void deleteById(String id);
}
