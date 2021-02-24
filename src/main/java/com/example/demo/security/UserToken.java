package com.example.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collections;

public class UserToken extends AbstractAuthenticationToken implements Serializable {
    public static final GrantedAuthority DEFAULT_ROLE = new SimpleGrantedAuthority("USER");

    private final String header;

    public UserToken(String header) {
        super(Collections.singleton(DEFAULT_ROLE));
        this.header = header;
    }

    @Override
    public Object getCredentials() {
        return header;
    }

    @Override
    public Object getPrincipal() {
        return header;
    }
}
