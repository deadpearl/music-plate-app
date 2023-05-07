package com.example.musicplate.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static jakarta.servlet.RequestDispatcher.*;

@WebServlet(name = "AppExceptionHandler", value = "/AppExceptionHandler")
public class AppExceptionHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage.jsp");
        dispatcher.forward(request, response);
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("<html><head><title>Error description</title></head><body>");
            writer.write("<h2>Error description</h2>");
            writer.write("<ul>");
            Arrays.asList(
                            ERROR_STATUS_CODE,
                            ERROR_EXCEPTION_TYPE,
                            ERROR_MESSAGE)
                    .forEach(e ->
                            writer.write("<li>" + e + ":" + request.getAttribute(e) + " </li>")
                    );
            writer.write("</ul>");
            writer.write("</html></body>");
        }
    }
}
