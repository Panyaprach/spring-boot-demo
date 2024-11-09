package com.example.demo.advicer;

import graphql.GraphQLError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.execution.SubscriptionExceptionResolverAdapter;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.String.format;

@Slf4j
@ControllerAdvice
public class GraphQLExceptionHandlerAdvice extends SubscriptionExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex) {
        log.error("GraphQL subscription error: {}", ex.getMessage(), ex);

        return super.resolveToSingleError(ex);
    }

    @GraphQlExceptionHandler
    public Collection<GraphQLError> handle(ConstraintViolationException ex) {
        ArrayList<GraphQLError> errors = new ArrayList<>();

        for (ConstraintViolation<?> violations : ex.getConstraintViolations()) {
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
