package ua.periodicals.controller;

import ua.periodicals.command.Command;
import ua.periodicals.command.CommandFactory;
import ua.periodicals.command.NextPageData;
import ua.periodicals.command.util.ResponseType;
import ua.periodicals.resource.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/controller")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        NextPageData nextPageData = null;

        CommandFactory commandFactory = new CommandFactory();
        Command command = commandFactory.defineCommand(req);

        nextPageData = command.execute(req);

        if (nextPageData.getPage() == null) {
            nextPageData.setPage(ConfigurationManager.getProperty("path.page.main"));
            nextPageData.setType(ResponseType.FORWARD);
        }
        
        switch (nextPageData.getType()) {
            case FORWARD:
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(nextPageData.getPage());
                dispatcher.forward(req, resp);
                break;
            case REDIRECT:
                resp.sendRedirect(nextPageData.getPage());
                break;
        }

    }

}
