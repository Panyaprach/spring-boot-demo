package com.example.demo.jpa.model.binding;

import com.example.demo.jpa.model.Role;

import java.util.HashMap;
import java.util.Map;

public interface View {
    Map<String, View> MAPPING = new HashMap<>() {
        {
            put(Role.ADMIN, new Admin());
            put(Role.USER, new User());
        }
    };

    int priority();


    class User implements View {
        @Override
        public int priority() {
            return 0;
        }
    }

    class Admin extends User {

        @Override
        public int priority() {
            return Integer.MAX_VALUE;
        }
    }
}
