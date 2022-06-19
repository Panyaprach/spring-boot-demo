package com.example.demo.jpa.model.binding;

import com.example.demo.jpa.model.Role;

import java.util.HashMap;
import java.util.Map;

public class View {
    public static final Map<String, Class> MAPPING = new HashMap<>();

    static {
        MAPPING.put(Role.ADMIN, Admin.class);
        MAPPING.put(Role.USER, User.class);
    }

    public static class User {
    }

    public static class Admin extends User {
    }
}
