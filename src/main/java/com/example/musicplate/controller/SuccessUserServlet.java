package com.example.musicplate.controller;

import com.example.musicplate.models.User;
import com.example.musicplate.repositories.impl.UserRepoImpl;
import com.example.musicplate.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/success-user")
public class SuccessUserServlet extends HttpServlet {
    UserService userService = new UserService(new UserRepoImpl());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        User user = new User(id, name, surname, email, pass, false);
//        try {
//            DBManager.updateUser(id, user);
//            resp.sendRedirect("/admin");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        userService.updateUser(id, user);
        resp.sendRedirect("/admin");

    }
}
