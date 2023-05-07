package com.example.musicplate.controller;

import com.example.musicplate.repositories.impl.UserRepoImpl;
import com.example.musicplate.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    UserService userService = new UserService(new UserRepoImpl());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

            //DBManager.removeUser(id);
            userService.removeUser(id);
            resp.sendRedirect("/admin");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
