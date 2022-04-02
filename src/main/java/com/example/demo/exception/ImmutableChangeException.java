package com.example.demo.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class ImmutableChangeException extends RuntimeException implements GraphQLError {

    public ImmutableChangeException() {
    }

    public ImmutableChangeException(String message) {
        super(message);
    }

    public ImmutableChangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImmutableChangeException(Throwable cause) {
        super(cause);
    }

    public ImmutableChangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.DataFetchingException;
    }
}