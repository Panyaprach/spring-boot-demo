package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.model.User;
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
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        Role adminRole = createRoleIfNotFound(Role.ADMIN);
        Role userRole = createRoleIfNotFound(Role.USER);

        String creator = "Application";

        User admin = User.builder()
                .withUsername("Admin")
                .withPassword(passwordEncoder.encode("admin"))
                .withRoles(Arrays.asList(adminRole))
                .withCreatedBy(creator)
                .withModifiedBy(creator)
//                .withCreatedAt(Instant.now())
//                .withModifiedAt(Instant.now())
                .build();
        User john = User.builder()
                .withUsername("John")
                .withPassword(passwordEncoder.encode("doe"))
                .withRoles(Arrays.asList(userRole))
                .withCreatedBy(creator)
                .withModifiedBy(creator)
//                .withCreatedAt(Instant.now())
//                .withModifiedAt(Instant.now())
                .build();
        userRepository.save(admin);
        userRepository.save(john);

        alreadySetup = true;
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
