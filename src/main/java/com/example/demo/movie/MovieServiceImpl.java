package com.example.demo.movie;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.movie.MovieSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository repository;

    public Movie create(Movie movie) {
        return repository.save(movie);
    }

    public Movie findById(String id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<Movie> findAll(MovieCriteria criteria) {
        Page<Movie> movies = findAll(criteria, Pageable.unpaged());

        return movies.toList();
    }

    @Override
    public Page<Movie> findAll(MovieCriteria criteria, Pageable pagination) {
        Specification<Movie> spec = where(nameContains(criteria.getName()))
                .and(categoryIs(criteria.getCategory()))
                .and(tagsContains(criteria.getTag()));

        return repository.findAll(spec, pagination);
    }

    public Movie update(Movie movie) {
        if (!repository.existsById(movie.getId())) {
            throw new ResourceNotFoundException();
        }

        return repository.save(movie);
    }

    public void deleteById(String id) {
        Movie movie = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        repository.delete(movie);
    }
}
