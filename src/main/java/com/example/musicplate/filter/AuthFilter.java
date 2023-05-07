package com.example.musicplate.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies();

        boolean isAdmin = false;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("current")) {
                if (cookie.getValue().equals("-1")) {
                    break;
                }
                isAdmin = true;
            }
        }

        if (!isAdmin) {
            System.out.println("You don't have access");
            res.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);// pass the request along the filter chain
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
