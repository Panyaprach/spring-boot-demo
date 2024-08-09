package com.example.demo.security;

import com.example.demo.exception.ExceptionDescriptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

@Component
public class APIAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String reason = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        int status = HttpStatus.UNAUTHORIZED.value();
        String path = urlPathHelper.getPathWithinApplication(request);

        ExceptionDescriptor descriptor = ExceptionDescriptor.builder()
                .withMessage(e.getMessage())
                .withError(reason)
                .withPath(path)
                .withStatus(status)
                .build();

        String body = objectMapper.writeValueAsString(descriptor);

        response.setStatus(status);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(body);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
