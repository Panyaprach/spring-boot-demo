package com.example.demo.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class TemplateFilter extends BaseFilter {

    protected abstract void preFilter(HttpServletRequest request, HttpServletResponse response);

    protected abstract void postFilter(HttpServletRequest request, HttpServletResponse response);

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        preFilter(httpServletRequest, httpServletResponse);
        chain.doFilter(request, response);
        postFilter(httpServletRequest, httpServletResponse);
    }
}
