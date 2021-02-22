package com.example.demo.advicer;

import com.example.demo.exception.ResourceNotFoundException;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    private static final String EMPTY = "";
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) throws Exception {
        String reason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        int status = HttpStatus.BAD_REQUEST.value();
        String path = urlPathHelper.getPathWithinApplication(request);
        String message = ex.getMessage() == null ? "Resource is not found!" : ex.getMessage();

        RestExceptionDescriptor descriptor = RestExceptionDescriptor.builder()
                .withError(reason)
                .withPath(path)
                .withStatus(status)
                .withMessage(message)
                .build();


        return ResponseEntity.badRequest()
                .body(descriptor);
    }


    @Getter
    @Setter
    @Builder(setterPrefix = "with")
    public static class RestExceptionDescriptor {
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
}
