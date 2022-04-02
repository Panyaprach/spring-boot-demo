package com.example.demo.movie;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MovieServiceTest {

    @MockBean
    MovieRepository repository;
    @Autowired
    private MovieService service;

    @Test
    public void whenFindById_thenFound() {
        Movie fiftyShadesOfGrey = Movie.builder()
                .withId("1")
                .withName("Fifty Shades of Grey")
                .withCategory(Category.EROTIC).build();

        when(repository.findById(Mockito.eq(fiftyShadesOfGrey.getId()))).thenReturn(Optional.of(fiftyShadesOfGrey));

        Movie found = service.findById(fiftyShadesOfGrey.getId());
        assertThat(found.getId(), is(fiftyShadesOfGrey.getId()));
    }

    @Test
    public void whenMovieNotExists_thenUpdateFail() {
        Movie fiftyShadesOfGrey = Movie.builder()
                .withId("1")
                .withName("Fifty Shades of Grey")
                .withCategory(Category.EROTIC).build();

        when(repository.existsById(fiftyShadesOfGrey.getId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.update(fiftyShadesOfGrey));
    }

    @Test
    public void whenMovieExists_thenUpdateSuccess() {
        Movie fiftyShadesOfGrey = Movie.builder()
                .withId("1")
                .withName("Fifty Shades of Grey")
                .withCategory(Category.EROTIC).build();

        when(repository.existsById(fiftyShadesOfGrey.getId())).thenReturn(true);
        when(repository.save(fiftyShadesOfGrey)).thenReturn(fiftyShadesOfGrey);

        Movie result = service.update(fiftyShadesOfGrey);

        assertEquals(fiftyShadesOfGrey, result);
    }

    @TestConfiguration
    static class MovieServiceImplTestContextConfiguration {

        @Bean
        public MovieService movieService() {
            return new MovieServiceImpl();
        }
    }
}