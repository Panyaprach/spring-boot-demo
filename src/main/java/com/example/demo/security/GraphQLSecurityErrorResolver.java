package com.example.demo.security;

import graphql.GraphQLError;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

public interface GraphQLSecurityErrorResolver {

    default GraphQLError resolve(AccessDeniedException ex) {
        return GraphQLError.newError()
                .errorType(ErrorType.FORBIDDEN)
                .message(ex.getMessage())
                .build();
    }

    default GraphQLError resolve(AuthenticationException ex) {
        return GraphQLError.newError()
                .errorType(ErrorType.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();
    }


}
