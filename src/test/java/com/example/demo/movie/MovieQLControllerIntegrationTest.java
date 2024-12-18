package com.example.demo.movie;

import com.example.demo.jpa.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureHttpGraphQlTester
public class MovieQLControllerIntegrationTest {

    @Autowired
    HttpGraphQlTester tester;

    @Test
    @WithMockUser(value = "Jame", authorities = {"USER"})
    public void givenQuery_whenResolve_thenSuccess() {
        String query = """
                query Movies {
                    movies(criteria: { category: ANIMATION }) {
                        id
                        name
                        category
                    }
                }
                """;

        tester.document(query)
                .execute()
                .path("data.movies[*].id")
                .entityList(String.class)
                .hasSize(4);
    }

    @Test
    @WithMockUser(value = "Jame", authorities = {"USER"})
    public void givenMultiQuery_whenResolve_thenSuccess() {
        String query = """
                {
                    dramaMovies: movies(criteria: { category: DRAMA }) { name }
                    comedyMovies: movies(criteria: { category: COMEDY }) { name }
                }
                """;

        tester.document(query)
                .execute()
                .path("data.dramaMovies[*].name").entityList(String.class).hasSize(13)
                .path("data.comedyMovies[*].name").entityList(String.class).hasSize(25);
    }

    @Test
    @WithMockUser(value = "Jame", authorities = {"USER"})
    public void givenBadQuery_whenResolve_thenBadRequest() {
        String query = """
                query Movies {
                    movies(page: -9, size: 200) {
                        name
                    }
                }
                """;

        tester.document(query)
                .execute()
                .errors()
                .expect(er -> er.getErrorType().equals(ErrorType.BAD_REQUEST));
    }

    @Test
    @WithMockUser(value = "Jame", authorities = {"USER"})
    public void givenMutation_whenResolve_thenSuccess() {
        String query = """
                mutation Movie {
                    movie(
                        input: {
                            name: "Avengers: Infinity War"
                            category: ACTION
                            tags: ["mavel"]
                            score: 91
                        }
                    ) {
                        id
                        name
                        category
                        score
                        tags
                        createdBy
                        createdAt
                        modifiedBy
                        modifiedAt
                    }
                }
                """;

        tester.document(query)
                .execute()
                .path("data.movie.id").hasValue()
                .path("data.movie.name").entity(String.class).isEqualTo("Avengers: Infinity War")
                .path("data.movie.category").entity(Category.class).isEqualTo(Category.ACTION)
                .path("data.movie.score").entity(Integer.class).isEqualTo(91)
                .path("data.movie.tags").entityList(String.class).isEqualTo(List.of("mavel"))
                .path("data.movie.createdBy").entity(String.class).isEqualTo("Jame")
                .path("data.movie.createdAt").hasValue()
                .path("data.movie.modifiedBy").entity(String.class).isEqualTo("Jame")
                .path("data.movie.modifiedAt").hasValue();
    }
}
