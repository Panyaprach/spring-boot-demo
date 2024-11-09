package com.example.demo.security;

import com.example.demo.exception.ExceptionDescriptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQLError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

@Component
public class APIAuthenticationEntryPoint implements AuthenticationEntryPoint, GraphQLSecurityErrorResolver {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    GraphQlProperties graphql;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String path = urlPathHelper.getPathWithinApplication(request);
        String gqlHttpPath = graphql.getPath();
        String gqlWSPath = graphql.getWebsocket().getPath();

        if (path.equals(gqlHttpPath) || path.equals(gqlWSPath)) {
            GraphQLError error = resolve(e);

            writeResponse(response, HttpStatus.OK, error);
        } else {
            String reason = HttpStatus.UNAUTHORIZED.getReasonPhrase();
            int status = HttpStatus.UNAUTHORIZED.value();

            ExceptionDescriptor error = ExceptionDescriptor.builder()
                    .withMessage(e.getMessage())
                    .withError(reason)
                    .withPath(path)
                    .withStatus(status)
                    .build();

            writeResponse(response, HttpStatus.UNAUTHORIZED, error);
        }
    }

    private void writeResponse(HttpServletResponse response, HttpStatus status, Object error) throws IOException {
        String body = objectMapper.writeValueAsString(error);

        response.setStatus(status.value());
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(body);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
