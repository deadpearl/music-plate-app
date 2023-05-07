package com.example.musicplate.controller;

import com.example.musicplate.db.HibernateUtil;
import com.example.musicplate.models.Cart;
import com.example.musicplate.repositories.impl.CartRepoImpl;
import com.example.musicplate.repositories.impl.PlateRepoImpl;
import com.example.musicplate.service.CartService;
import com.example.musicplate.service.PlateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/buy-plate")
public class BuyPlateServlet extends HttpServlet {

    private final PlateService plateService = new PlateService(new PlateRepoImpl(HibernateUtil.getSession().getSessionFactory()));
    private final CartService cartService = new CartService(new CartRepoImpl(HibernateUtil.getSession().getSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String page = req.getParameter("page");
        Cookie[] cookies = req.getCookies();
        boolean isAuth = false;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("current")) {
                if (cookie.getValue().equals("-1")) break;
                Long current = Long.valueOf(cookie.getValue());
                    //DBManager.addCart(current, id);
                    Cart cart = new Cart(current,id);
                    cartService.addCart(cart);
                    if (page != null)
                        resp.sendRedirect("/main");
                    else
                        resp.sendRedirect("/products");

            }
        }
    }
}
