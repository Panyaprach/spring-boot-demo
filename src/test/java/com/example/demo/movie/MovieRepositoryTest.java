package com.example.demo.movie;

import com.example.demo.config.AuditorTestConfiguration;
import com.example.demo.extension.SpringPrintSqlExtension;
import com.example.demo.jpa.model.Category;
import com.example.demo.jpa.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.demo.movie.MovieSpecification.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.jpa.domain.Specification.where;

@DataJpaTest
@ExtendWith({SpringExtension.class, SpringPrintSqlExtension.class})
@Import(AuditorTestConfiguration.class)
class MovieRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MovieRepository repository;
    private Movie loveSiren, venom, aquaman, avenger;

    @BeforeEach
    public void setup() {
        loveSiren = Movie.builder().withName("Love Siren")
                .withCategory(Category.ROMANCE).build();
        venom = Movie.builder().withName("Venom")
                .withTags(List.of("epic"))
                .withCategory(Category.FANTASY).build();
        aquaman = Movie.builder().withName("Aquaman")
                .withTags(List.of("epic"))
                .withCategory(Category.FANTASY).build();
        avenger = Movie.builder().withName("Avenger")
                .withTags(List.of("epic", "best-seller"))
                .withCategory(Category.FANTASY).build();

        persistAll(loveSiren, venom, aquaman, avenger);
        // reload to avoid jpa hibernate instance trouble
        loveSiren = load(loveSiren);
        venom = load(venom);
        aquaman = load(aquaman);
        avenger = load(avenger);
    }

    private Movie load(Movie movie) {
        return em.find(Movie.class, movie.getId());
    }

    private void persistAll(Movie... movies) {
        Stream.of(movies)
                .forEach(this::persist);
    }

    private void persist(Movie movie) {
        em.persist(movie);
        em.flush();
        em.detach(movie);
        assertNotNull(movie.getId(), "Persist movie must generate id");
    }

    @Test
    public void whenFindAll_withNameContains_thenCorrect() {
        List<Movie> result = repository.findAll(where(nameContains("m")));
        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(aquaman, venom));
    }

    @Test
    public void whenFindAll_withCategoryIs_thenCorrect() {
        List<Movie> result = repository.findAll(where(categoryIs(Category.FANTASY)));
        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(venom, aquaman, avenger));
    }

    @Test
    public void whenFindById_thenCorrect() {
        Optional<Movie> result = repository.findById(venom.getId());
        assertTrue(result.isPresent());
        assertThat(result.get(), is(venom));
    }

    @Test
    public void whenFindAll_withTagsContain_thenCorrect() {
        List<Movie> result = repository.findAll(where(tagsContains("best-seller")));

        assertThat(result, hasSize(1));
        assertThat(result, contains(avenger));

        result = repository.findAll(where(tagsContains("epic")));
        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(avenger, venom, aquaman));
    }

    @Test
    public void whenUpdate_thenOk() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("TBA");
        avenger.setTags(tags);
        Movie result = repository.save(avenger);
        assertEquals(avenger, result);
        assertThat(result.getTags(), contains("TBA"));
    }

}