package com.example.demo.movie;

import com.example.demo.model.Category;
import com.example.demo.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = MovieController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(SpringExtension.class)
class MovieControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    MovieService service;

    @Test
    public void whenGetById_thenSuccess() throws Exception {
        Movie fiftyShadesOfGrey = Movie.builder()
                .withId("1")
                .withName("Fifty Shades of Grey")
                .withCategory(Category.EROTIC).build();

        when(service.findById(Mockito.eq(fiftyShadesOfGrey.getId())))
                .thenReturn(fiftyShadesOfGrey);

        final String path = String.join("/", MovieController.PATH, fiftyShadesOfGrey.getId());
        mvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(fiftyShadesOfGrey.getId())));
    }

    @Test
    public void whenGetAll_thenSuccess() throws Exception {
        Movie fiftyShadesOfGrey = Movie.builder()
                .withId("1")
                .withName("Fifty Shades of Grey")
                .withCategory(Category.EROTIC).build();
        Movie demonSlayer = Movie.builder()
                .withId("2")
                .withName("Demon Slayer")
                .withCategory(Category.ANIMATION).build();

        when(service.findAll(any())).thenReturn(List.of(fiftyShadesOfGrey, demonSlayer));

        mvc.perform(get(MovieController.PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(fiftyShadesOfGrey.getName())))
                .andExpect(jsonPath("$[1].name", is(demonSlayer.getName())));
    }
}