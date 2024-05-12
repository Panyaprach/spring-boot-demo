package com.example.demo;

import com.example.demo.jpa.model.Category;
import com.example.demo.jpa.model.Movie;
import com.example.demo.jpa.model.Role;
import com.example.demo.jpa.model.User;
import com.example.demo.movie.MovieRepository;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        createDefaultUsers();
        createSampleMovie();

        alreadySetup = true;
    }

    private void createDefaultUsers() {
        Role adminRole = createRoleIfNotFound(Role.ADMIN);
        Role userRole = createRoleIfNotFound(Role.USER);

        String creator = "Application";

        User admin = User.builder()
                .withUsername("Admin")
                .withPassword(passwordEncoder.encode("admin"))
                .withRoles(Arrays.asList(adminRole, userRole))
                .withCreatedBy(creator)
                .withModifiedBy(creator)
                .withCreatedAt(Instant.now())
                .withModifiedAt(Instant.now())
                .build();
        User john = User.builder()
                .withUsername("John")
                .withPassword(passwordEncoder.encode("doe"))
                .withRoles(Arrays.asList(userRole))
                .withCreatedBy(creator)
                .withModifiedBy(creator)
                .withCreatedAt(Instant.now())
                .withModifiedAt(Instant.now())
                .build();
        userRepository.save(admin);
        userRepository.save(john);
        log.info("Registered users");
    }

    private void createSampleMovie() {
        try {
            log.info("... Loading sample movies");
            List<Movie> movies = sampleMovies();
            movieRepository.saveAll(movies);
        } catch (IOException e) {
            log.error("Unable to load sample movies", e);
        }
    }

    private List<Movie> sampleMovies() throws IOException {
        File file = ResourceUtils.getFile("classpath:data/movies.csv");

        return Files.readAllLines(file.toPath()).stream()
                .skip(1)
                .map(src -> src.split(","))
                .map(List::of)
                .map(this::fromCSVLine)
                .collect(Collectors.toList());
    }

    private Movie fromCSVLine(List<String> content) {
        String name = content.get(0);
        String genre = content.get(1);
        String studio = content.get(2);
        Category category = Category.valueOf(genre.toUpperCase());
        Double profit = Double.parseDouble(content.get(4));
        Integer year = Integer.parseInt(content.get(content.size() - 1));
        Instant instant = timestamp(year);

        return Movie.builder()
                .withName(name)
                .withCategory(category)
                .withProfitability(profit)
                .withCreatedBy(studio)
                .withModifiedBy(studio)
                .withCreatedAt(instant)
                .withModifiedAt(instant)
                .build();
    }

    private Instant timestamp(int year) {
        Calendar calendar = Calendar.getInstance();
        Instant random = Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt());
        calendar.setTimeInMillis(random.toEpochMilli());
        calendar.set(Calendar.YEAR, year);

        return calendar.toInstant();
    }

    @Transactional
    Role createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
