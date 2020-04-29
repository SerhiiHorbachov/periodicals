package ua.periodicals.controller;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.command.factory.ActionFactory;
import ua.periodicals.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {
        "/admin/periodicals",
        "/admin/new-periodical",
        "/register",
        "/login"
})
public class FrontController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NextPage nextPage = null;

        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);

        nextPage = command.execute(request);


        if (nextPage.getDispatchType().equals("FORWARD")) {
            RoutingUtils.forwardToPage(nextPage.getPage(), request, response);
        } else if (nextPage.getDispatchType().equals("REDIRECT")) {
            RoutingUtils.forwardToPage(nextPage.getPage(), request, response);
        }

    }
}
