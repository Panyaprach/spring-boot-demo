package com.example.demo.security;


import com.example.demo.exception.ExceptionDescriptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQLError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

@Component
public class APIAccessDeniedHandler implements AccessDeniedHandler, GraphQLSecurityErrorResolver {

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    GraphQlProperties graphql;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        String path = urlPathHelper.getPathWithinApplication(request);
        String gqlHttpPath = graphql.getPath();
        String gqlWSPath = graphql.getWebsocket().getPath();

        if (path.equals(gqlHttpPath) || path.equals(gqlWSPath)) {
            GraphQLError error = resolve(e);

            writeResponse(response, HttpStatus.OK, error);
        } else {
            String reason = HttpStatus.FORBIDDEN.getReasonPhrase();
            int status = HttpStatus.FORBIDDEN.value();

            ExceptionDescriptor descriptor = ExceptionDescriptor.builder()
                    .withMessage(e.getMessage())
                    .withError(reason)
                    .withPath(path)
                    .withStatus(status)
                    .build();

            writeResponse(response, HttpStatus.FORBIDDEN, descriptor);
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
