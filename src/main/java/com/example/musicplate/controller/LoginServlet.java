package com.example.musicplate.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.musicplate.models.User;
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
import java.util.ArrayList;

@WebServlet("/entry")
public class LoginServlet extends HttpServlet {

    UserService userService = new UserService(new UserRepoImpl());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

            ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
                    //DBManager.getAllUsers();
            Long id = -1L;
            for(User user: users) {
                if(user.getEmail().equals(email) &&
                        BCrypt.verifyer().verify(password.toCharArray(),  user.getPassword()).verified) {
                    id = user.getId();
                    System.out.println("check is true");
                }
            }

            if(id == -1) getServletContext().getRequestDispatcher("/login").forward(req, resp);

            Cookie cookie = new Cookie("current", String.valueOf(id));
            cookie.setMaxAge(24 * 60 * 60);
            resp.addCookie(cookie);
            resp.sendRedirect("/main");


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
