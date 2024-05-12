package com.example.demo.advicer;

import com.example.demo.jpa.model.binding.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

@Log4j2
@RestControllerAdvice
public class SecurityJsonViewResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && hasAuthorities();
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue body, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        Class<?> bodyType = identifyElementType(body.getValue());
        if (!isJsonViewPresentInherited(bodyType)) {
            log.warn("Ignoring @JsonView serialization for class {}", returnType.getContainingClass());

            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(View.MAPPING::get)
                .reduce((a, b) -> a.priority() > b.priority() ? a : b)
                .ifPresent(view -> body.setSerializationView(view.getClass()));
    }

    protected boolean isJsonViewPresentInherited(Class<?> type) {
        while (type != null) {
            if (type.isAnnotationPresent(JsonView.class)) {
                return true;
            }

            // Check all method
            for (Method method : type.getDeclaredMethods()) {
                if (method.isAnnotationPresent(JsonView.class)) {
                    return true;
                }
            }
            // Check all field
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(JsonView.class)) {
                    return true;
                }
            }
            type = type.getSuperclass();
        }

        return false;
    }

    protected Class<?> identifyElementType(Object value) {
        if (value instanceof Collection) {
            Iterator iterator = ((Collection) value).iterator();

            return iterator.hasNext() ?
                    iterator.next().getClass() : value.getClass();
        } else {
            return value.getClass();
        }
    }

    protected boolean hasAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null;
    }
}
