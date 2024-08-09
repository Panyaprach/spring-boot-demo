package com.example.demo.security;

import com.example.demo.servlet.TemplateFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class HeaderAuthenticationFilter extends TemplateFilter {
    public static final String AUTH_HEADER = "X-User";

    @Override
    protected void preFilter(HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(request.getHeader(AUTH_HEADER))
                .ifPresent(this::authenticate);
    }

    @Override
    protected void postFilter(HttpServletRequest request, HttpServletResponse response) {
        // do nothing
    }

    protected void authenticate(String header) {
        Authentication authentication = new UserToken(header);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
