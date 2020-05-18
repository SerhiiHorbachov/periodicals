package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.impl.PeriodicalLogicImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainView implements ActionCommand {

    private static final int ITEMS_PER_PAGE = 9;
    private static final String PERIODICALS_ATTR = "periodicals";
    private static final String PAGE_PARAM = "page";

    private static final Logger LOG = LoggerFactory.getLogger(MainView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show main view, page={}", request.getParameter(PAGE_PARAM));

        PeriodicalLogicImpl periodicalLogicImpl = new PeriodicalLogicImpl();

        int page = 1;

        if (request.getParameter(PAGE_PARAM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAM));
        }

        long totalPages = (long) Math.ceil(((double) periodicalLogicImpl.getCount() / ITEMS_PER_PAGE));

        List<Periodical> periodicals = periodicalLogicImpl.getPerPage(page, ITEMS_PER_PAGE);
        request.setAttribute(PERIODICALS_ATTR, periodicals);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("activePage", page);
        request.setAttribute("invalidId", request.getParameter("inv_id"));

        return new NextPage("main.jsp", "FORWARD");
    }
}
