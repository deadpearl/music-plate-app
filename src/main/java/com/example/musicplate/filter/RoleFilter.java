package com.example.musicplate.filter;

import com.example.musicplate.models.User;
import com.example.musicplate.repositories.impl.UserRepoImpl;
import com.example.musicplate.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebFilter(filterName = "RoleFilter")
public class RoleFilter implements Filter {
    UserService userService = new UserService(new UserRepoImpl());


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
                    if(cookie.getValue().equals("-1")) {
                        break;
                    }
                   // User user = DBManager.getUser(Long.parseLong(cookie.getValue()));
                    User user = userService.getUser(Long.parseLong(cookie.getValue()));
                    if(user == null) break;
                    isAdmin = user.isAdmin();
                }
            }


        if (!isAdmin) {
            //this.context.log("Unauthorized access request");
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
