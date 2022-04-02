package com.example.demo.servlet;

import com.example.demo.exception.ExceptionDescriptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Bucket bucket;
    @Autowired
    private ObjectMapper mapper;

    public RateLimitInterceptor() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));

            return true;
        } else {
            long waitForRefill = NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill());
            ExceptionDescriptor descriptor = createDescription(request);
            String json = mapper.writeValueAsString(descriptor);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.setStatus(descriptor.getStatus());
            response.getWriter().write(json);

            return false;
        }
    }

    private ExceptionDescriptor createDescription(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        String reason = HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase();
        int status = HttpStatus.TOO_MANY_REQUESTS.value();
        String path = helper.getPathWithinApplication(request);
        String message = "rate exceeded";

        return ExceptionDescriptor.builder()
                .withError(reason)
                .withPath(path)
                .withStatus(status)
                .withMessage(message)
                .build();
    }
}
