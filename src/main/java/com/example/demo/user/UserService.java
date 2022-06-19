package com.example.demo.user;

import com.example.demo.jpa.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
}
