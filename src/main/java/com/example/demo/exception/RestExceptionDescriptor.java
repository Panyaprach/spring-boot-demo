package com.example.demo.exception;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.time.ZonedDateTime;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Data
@Builder(setterPrefix = "with")
public class RestExceptionDescriptor {
    @Default
    private ZonedDateTime timestamp = ZonedDateTime.now();

    private int status;

    @Default
    private String error = EMPTY;

    @Default
    private String message = EMPTY;

    @Default
    private String path = EMPTY;
}