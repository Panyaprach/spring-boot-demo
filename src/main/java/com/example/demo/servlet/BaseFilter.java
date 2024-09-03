package com.example.demo.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseFilter implements Filter {
    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Initialize filter: {}", getClass().getSimpleName());
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        log.info("Destroy filter: {}", getClass().getSimpleName());
    }
}
