package com.example.demo.movie;

import com.example.demo.DemoApplication;
import com.example.demo.config.SecurityConfiguration;
import com.example.demo.extension.SpringPrintSqlExtension;
import com.example.demo.model.Category;
import com.example.demo.model.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringPrintSqlExtension.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = DemoApplication.class)
@Import({
        SecurityConfiguration.class
})
public class MovieControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private MovieRepository repository;

    @AfterEach
    private void reset() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser("Jame")
    public void givenExistId_whenGetById_thenSuccess() throws Exception {
        Movie demonSlayer = createTestMovie("Demon Slayer", Category.ANIMATION);
        assertNotNull(demonSlayer.getId(), "Movie id is not generated!");

        final String pathWithId = String.join("/", MovieController.PATH, demonSlayer.getId());

        mvc.perform(get(pathWithId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(demonSlayer.getId())))
                .andExpect(jsonPath("$.name", is(demonSlayer.getName())));
    }

    @Test
    @WithMockUser("Tanny")
    public void givenNonExistId_whenGetById_thenNotFound() throws Exception {
        final String pathWithId = String.join("/", MovieController.PATH, "crazy");

        mvc.perform(get(pathWithId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.path", is(pathWithId)))
                .andExpect(jsonPath("$.message", notNullValue()))
        ;
    }

    @Test
    @WithMockUser("John")
    public void givenInvalidCategory_whenGetAll_shouldHandled() throws Exception {
        String invalidCategory = "crazy";

        mvc.perform(get(MovieController.PATH + "?category={0}", invalidCategory)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private Movie createTestMovie(String name, Category category) {
        Movie movie = Movie.builder()
                .withName(name).withCategory(category)
                .build();
        return repository.saveAndFlush(movie);
    }
}
