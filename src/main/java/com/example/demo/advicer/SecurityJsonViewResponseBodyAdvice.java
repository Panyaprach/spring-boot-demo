package com.example.demo.advicer;

import com.example.demo.model.binding.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class SecurityJsonViewResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && hasAuthorities();
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue body, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        Class<?> bodyType = body.getValue().getClass();
        if (!isJsonViewPresentInherited(bodyType)) {
            log.warn("Ignoring @JsonView serialization for class {}", returnType.getContainingClass());

            return;
        }

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<Class> jsonViews = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(View.MAPPING::get)
                .collect(Collectors.toList());

        if (jsonViews.size() != 1) {
            throw new IllegalArgumentException("Ambiguous @JsonView mapping for roles "
                    + authorities.stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        }

        body.setSerializationView(jsonViews.get(0));
    }

    public boolean isJsonViewPresentInherited(Class<?> type) {
        while (type != null) {
            if(type.isAnnotationPresent(JsonView.class)) {
                return true;
            }

            // Check all method
            for (Method method: type.getDeclaredMethods()) {
                if(method.isAnnotationPresent(JsonView.class)) {
                    return true;
                }
            }
            // Check all field
            for(Field field: type.getDeclaredFields()){
                if(field.isAnnotationPresent(JsonView.class)) {
                    return true;
                }
            }
            type = type.getSuperclass();
        }

        return false;
    }

    protected boolean hasAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null;
    }
}
