package com.example.musicplate.controller;

import com.example.musicplate.models.User;
import com.example.musicplate.repositories.impl.UserRepoImpl;
import com.example.musicplate.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reg")
public class RegisterServlet extends HttpServlet {
    UserService userService = new UserService(new UserRepoImpl());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String page = req.getParameter("page");

        User user = new User(1L, name, surname, email, BCrypt.hashpw(password, BCrypt.gensalt()));
//        try {
//            DBManager.addUser(user);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        userService.addUser(user);

        if (page != null)
            resp.sendRedirect("/admin");
        else
            resp.sendRedirect("/login");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
