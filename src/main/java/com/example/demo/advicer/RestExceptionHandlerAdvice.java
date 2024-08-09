package com.example.demo.advicer;

import com.example.demo.exception.ExceptionDescriptor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnmodifiedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UrlPathHelper;

import java.util.stream.Collectors;

@ControllerAdvice
public final class RestExceptionHandlerAdvice {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(cv -> String.join(" ", cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.joining(", "));

        return handleBadRequest(request, message);
    }

    @ExceptionHandler({
            ResourceNotFoundException.class,
            UnmodifiedException.class
    })
    private ResponseEntity<?> handleClientError(Exception ex, HttpServletRequest request) {
        return handleBadRequest(request, ex.getMessage());
    }

    private ResponseEntity<?> handleBadRequest(HttpServletRequest request, String message) {
        String reason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        int status = HttpStatus.BAD_REQUEST.value();
        String path = urlPathHelper.getPathWithinApplication(request);

        ExceptionDescriptor descriptor = ExceptionDescriptor.builder()
                .withError(reason)
                .withPath(path)
                .withStatus(status)
                .withMessage(message)
                .build();

        return ResponseEntity.badRequest()
                .body(descriptor);
    }

}
