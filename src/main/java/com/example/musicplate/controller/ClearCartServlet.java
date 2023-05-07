package com.example.musicplate.controller;

import com.example.musicplate.db.HibernateUtil;
import com.example.musicplate.models.User;
import com.example.musicplate.repositories.UserRepo;
import com.example.musicplate.repositories.impl.UserRepoImpl;
import com.example.musicplate.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/clear")
public class ClearCartServlet extends HttpServlet {

    UserService userService = new UserService(new UserRepoImpl());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        boolean isAuth = false;
        long id = -1L;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("current")) {
                if(cookie.getValue().equals("-1")) {
                    break;
                }
//                    User user = DBManager.getUser(Long.valueOf(cookie.getValue()));
//                    assert user != null;
//                    DBManager.removeCart(user.getId());
//                    resp.sendRedirect("/cart");

                    User user = userService.getUser(Long.valueOf(cookie.getValue()));
                    assert  user!=null;
                    userService.removeCart(user.getId());
                    resp.sendRedirect("/cart");



            }
        }
    }
}
