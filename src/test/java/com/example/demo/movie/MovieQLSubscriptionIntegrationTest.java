package com.example.demo.movie;

import com.example.demo.DemoApplication;
import com.example.demo.config.GraphQLConfiguration;
import com.example.demo.config.SecurityConfiguration;
import com.example.demo.jpa.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.graphql.AutoConfigureGraphQl;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureGraphQl
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {
        DemoApplication.class, GraphQLConfiguration.class, SecurityConfiguration.class
})

// Seem like today (11/9/2024), It is not possible to use Mock integration test with WebSocket!
public class MovieQLSubscriptionIntegrationTest {

    @LocalServerPort
    private int port;
    WebSocketGraphQlTester tester;

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

        Flux<Movie> result = tester.mutate()
                .headers(header -> header.setBasicAuth("admin", "admin"))
                .build()
                .document(query)
                .executeSubscription()
                .toFlux("data.newMovie", Movie.class);

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextMatches(movie -> movie.getName().equals("Killers"))
                .expectComplete()
                .verify();
    }
}
