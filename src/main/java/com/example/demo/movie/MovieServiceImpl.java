package com.example.demo.movie;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.movie.MovieSpecification.categoryIs;
import static com.example.demo.movie.MovieSpecification.nameContains;
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
        Specification<Movie> spec = where(nameContains(criteria.getName()))
                .and(categoryIs(criteria.getCategory()));

        return repository.findAll(spec);
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
