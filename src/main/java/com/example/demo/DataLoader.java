package com.example.demo;

import com.example.demo.jpa.model.Category;
import com.example.demo.jpa.model.Movie;
import com.example.demo.jpa.model.Role;
import com.example.demo.jpa.model.User;
import com.example.demo.movie.MovieRepository;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;

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
                .withRoles(Arrays.asList(adminRole))
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
    }

    private void createSampleMovie() {
        String creator = "Application";

        Movie fiftyShadesOfGrey = Movie.builder()
                .withId("1")
                .withName("Fifty Shades of Grey")
                .withCategory(Category.EROTIC)
                .withCreatedAt(Instant.now())
                .withCreatedBy(creator)
                .withModifiedAt(Instant.now())
                .withModifiedBy(creator)
                .build();

        movieRepository.save(fiftyShadesOfGrey);
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
