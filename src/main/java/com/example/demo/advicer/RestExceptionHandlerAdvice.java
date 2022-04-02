package com.example.demo.advicer;

import com.example.demo.exception.ExceptionDescriptor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnmodifiedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public final class RestExceptionHandlerAdvice {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @ExceptionHandler({
            ResourceNotFoundException.class,
            UnmodifiedException.class
    })
    private ResponseEntity<?> handleBadRequest(Exception ex, HttpServletRequest request) {
        String reason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        int status = HttpStatus.BAD_REQUEST.value();
        String path = urlPathHelper.getPathWithinApplication(request);
        String message = ex.getMessage();

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
