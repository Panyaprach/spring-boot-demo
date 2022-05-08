package com.example.demo.movie;

import com.example.demo.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    Movie create(Movie movie);

    Movie findById(String id);

    List<Movie> findAll(MovieCriteria criteria);

    Page<Movie> findAll(MovieCriteria criteria, Pageable pagination);

    Movie update(Movie movie);

    void deleteById(String id);
}
