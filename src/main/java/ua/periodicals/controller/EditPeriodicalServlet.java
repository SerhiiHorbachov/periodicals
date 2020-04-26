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

@WebServlet("/admin/edit-periodical")
public class EditPeriodicalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PeriodicalLogicImpl periodicalLogicImpl = new PeriodicalLogicImpl();

        Long id = Long.parseLong(request.getParameter("periodicalId"));

        String name = request.getParameter("name");
        System.out.println(name);

        int price = (int) Float.parseFloat(request.getParameter("price")) * 100;
        System.out.println(price);

        String description = request.getParameter("description");
        System.out.println(description);

        Periodical periodical = new Periodical(id, name, description, price);
        periodicalLogicImpl.update(periodical);

        RoutingUtils.redirect("/admin/periodicals", request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PeriodicalLogicImpl periodicalLogicImpl = new PeriodicalLogicImpl();

        Long id = Long.parseLong(request.getParameter("id"));
        Periodical periodical = periodicalLogicImpl.findById(id);

        request.setAttribute("periodical", periodical);


        RoutingUtils.forwardToPage("admin/editPeriodical.jsp", request, response);
    }
}
