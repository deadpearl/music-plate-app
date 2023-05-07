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

@WebServlet("/create-plate")
public class CreatePlateServlet extends HttpServlet {
    private final PlateService plateService = new PlateService(new PlateRepoImpl(HibernateUtil.getSession().getSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String preview = req.getParameter("preview");
        String desc = req.getParameter("desc");
        int price = Integer.parseInt(req.getParameter("price"));

        Plate plate = new Plate(1L, name, preview, desc, price);

            //DBManager.addPlate(plate);
            plateService.addPlate(plate);


        resp.sendRedirect("/admin");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
