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

@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {

    UserService userService = new UserService(new UserRepoImpl());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

            User user = userService.getUser(id);
           // User user = DBManager.getUser(id);
            if(user == null) resp.sendRedirect("/admin");
            req.setAttribute("user", user);
            getServletContext().getRequestDispatcher("/user").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
