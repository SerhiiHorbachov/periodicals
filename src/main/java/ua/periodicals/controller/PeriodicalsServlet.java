package ua.periodicals.controller;

import ua.periodicals.model.Periodical;
import ua.periodicals.service.impl.PeriodicalLogicImpl;
import ua.periodicals.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/admin/periodicals")
public class PeriodicalsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   /*     PeriodicalLogicImpl periodicalLogicImpl = new PeriodicalLogicImpl();
        List<Periodical> periodicals = periodicalLogicImpl.findAll();

        System.out.println("[INFO] METHOD: " + request.getMethod() + request.getServletPath());
        System.out.println("***");

        request.setAttribute("periodicals", periodicals);

        RoutingUtils.forwardToPage("admin/periodicals.jsp", request, response);

    */
    }
}
