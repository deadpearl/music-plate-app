package com.example.musicplate.controller;

import com.example.musicplate.db.HibernateUtil;
import com.example.musicplate.repositories.impl.PlateRepoImpl;
import com.example.musicplate.service.PlateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-plate")
public class DeletePlateServlet extends HttpServlet {

        private final PlateService plateService = new PlateService(new PlateRepoImpl(HibernateUtil.getSession().getSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

            //DBManager.removePlate(id);
        plateService.removePlate(id);
            resp.sendRedirect("/admin");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
