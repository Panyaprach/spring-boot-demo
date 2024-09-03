package com.example.demo.movie;

import com.example.demo.jpa.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.graphql.data.GraphQlRepository;
import org.springframework.stereotype.Repository;

@Repository
@GraphQlRepository
public interface MovieRepository
        extends JpaRepository<Movie, String>, JpaSpecificationExecutor<Movie>, QueryByExampleExecutor<Movie> {
}