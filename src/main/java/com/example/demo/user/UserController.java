package com.example.demo.user;

import com.example.demo.jpa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UserController.PATH)
public class UserController {
    public final static String PATH = "/users";

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = service.findAll();

        return ResponseEntity.ok(users);
    }
}
