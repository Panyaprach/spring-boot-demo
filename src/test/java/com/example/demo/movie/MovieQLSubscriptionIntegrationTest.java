package com.example.demo.movie;

import com.example.demo.graphql.scalar.InstantScalar;
import com.example.demo.jpa.model.Category;
import com.example.demo.jpa.model.Movie;
import com.example.demo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Disabled("incomplete")
@GraphQlTest
@TestPropertySource(properties = {
        "local.server.port=9999"
})
public class MovieQLSubscriptionIntegrationTest {

    @MockBean
    MovieService service;
    @MockBean
    UserService userService;
    WebSocketGraphQlTester tester;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        tester = WebSocketGraphQlTester.builder("ws://localhost:" + port + "/graphql", client).build();
    }

    @Test
    public void givenSubscription_whenSubscribe_thenSuccess() {
        String query = """
                subscription NewMovie {
                    newMovie(criteria: { category: ACTION }) {
                        id
                        name
                        category
                        tags
                        score
                        createdBy
                        createdAt
                    }
                }
                """;


        Movie demonSlayer = Movie.builder()
                .withId("2")
                .withName("Demon Slayer")
                .withCategory(Category.ANIMATION).build();
        when(service.findAll(any())).thenReturn(List.of(demonSlayer));

        Flux<Movie> result = tester.document(query)
                .executeSubscription()
                .toFlux("data.newMovie", Movie.class);

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextMatches(movie -> movie.getName().equals("Killers"))
                .expectComplete()
                .verify();
    }

    @TestConfiguration
    static class GraphQLTestConfiguration {
        @Bean
        public RuntimeWiringConfigurer runtimeWiringConfigurer() {

            return wiring -> wiring.scalar(InstantScalar.INSTANCE);
        }
    }
}
