package com.example.demo.advicer;

import graphql.GraphQLError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.String.format;

@ControllerAdvice
public class GraphQLExceptionHandlerAdvice {

    @GraphQlExceptionHandler
    public GraphQLError handle(BindException ex) {
        return GraphQLError.newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
    }

    @GraphQlExceptionHandler
    public Collection<GraphQLError> handle(ConstraintViolationException ex) {
        ArrayList<GraphQLError> errors = new ArrayList<>();
        
        for(ConstraintViolation<?> violations: ex.getConstraintViolations()) {
            String message = format("%s %s", violations.getPropertyPath(), violations.getMessage());

            GraphQLError error = GraphQLError.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(message)
                    .build();

            errors.add(error);
        }
        return errors;
    }
}
