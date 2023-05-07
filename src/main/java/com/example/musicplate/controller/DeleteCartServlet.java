package com.example.musicplate.controller;

import com.example.musicplate.db.HibernateUtil;
import com.example.musicplate.repositories.impl.CartRepoImpl;
import com.example.musicplate.repositories.impl.PlateRepoImpl;
import com.example.musicplate.service.CartService;
import com.example.musicplate.service.PlateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-cart")
public class DeleteCartServlet extends HttpServlet {
    private final PlateService plateService = new PlateService(new PlateRepoImpl(HibernateUtil.getSession().getSessionFactory()));
    private final CartService cartService = new CartService(new CartRepoImpl(HibernateUtil.getSession().getSessionFactory()));


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long plate_id = Long.valueOf(req.getParameter("plate_id"));
        Long user_id = Long.valueOf(req.getParameter("user_id"));

            //DBManager.removeCartByPlate(user_id, plate_id);
            cartService.removeCartByPlate(user_id, plate_id);
            resp.sendRedirect("/cart");

    }
}
