package com.example.musicplate.controller;

import com.example.musicplate.db.HibernateUtil;
import com.example.musicplate.models.Plate;
import com.example.musicplate.repositories.impl.PlateRepoImpl;
import com.example.musicplate.service.PlateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/success-plate")
public class SuccessPlateServlet extends HttpServlet {

    private final PlateService plateService = new PlateService(new PlateRepoImpl(HibernateUtil.getSession().getSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String preview = req.getParameter("preview");
        String descriptions = req.getParameter("description");
        int price = Integer.parseInt(req.getParameter("price"));
        Plate plate = new Plate(id, name, preview, descriptions, price);
//        try {
//            DBManager.updatePlate(id, plate);
//            resp.sendRedirect("/admin");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        plateService.updatePlate(id, plate);
        resp.sendRedirect("/admin");
    }
}
