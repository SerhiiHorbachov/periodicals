package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.impl.PeriodicalLogicImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainView implements ActionCommand {

    private static final int ITEMS_PER_PAGE = 5;

    @Override
    public NextPage execute(HttpServletRequest request) {

        PeriodicalLogicImpl periodicalLogicImpl = new PeriodicalLogicImpl();
//        List<Periodical> periodicals = periodicalLogicImpl.findAll();

        int page = 1;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        long totalPages = (long) Math.ceil(((double) periodicalLogicImpl.getCount() / ITEMS_PER_PAGE));

        List<Periodical> periodicals = periodicalLogicImpl.getPerPage(page, ITEMS_PER_PAGE);
        request.setAttribute("periodicals", periodicals);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("activePage", page);

        return new NextPage("main.jsp", "FORWARD");
    }
}
