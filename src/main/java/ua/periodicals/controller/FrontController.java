package ua.periodicals.controller;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.command.factory.ActionFactory;
import ua.periodicals.util.DispatchType;
import ua.periodicals.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.periodicals.util.Pages.*;

@WebServlet(
    urlPatterns = {
        MAIN_PATH,
        ADMIN_PERIODICALS_PATH,
        ADMIN_INVOICES_PATH,
        ADMIN_NEW_PERIODICAL_PATH,
        ADMIN_EDIT_PERIODICAL_PATH,
        ADMIN_INVOICES_IN_PROGRESS_PATH,
        ADMIN_INVOICES_VIEW_PATH,
        MY_CART_PATH,
        MY_SUBSCRIPTIONS_PATH,
        ADD_TO_CART_PATH,
        REGISTER_PATH,
        LOGIN_PATH,
        LOGOUT_PATH
    }

)
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

        if (nextPage.getDispatchType() == DispatchType.FORWARD) {
            RoutingUtils.forwardToPage(nextPage.getPage(), request, response);
        } else if (nextPage.getDispatchType() == DispatchType.REDIRECT) {
            RoutingUtils.redirect(nextPage.getPage(), request, response);
        }

    }
}
