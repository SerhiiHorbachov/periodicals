package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ListPeriodicals implements ActionCommand {

    private static final int ITEMS_PER_PAGE = 10;
    private static final String PERIODICALS_ATTR = "periodicals";
    private static final String PAGE_PARAM = "page";

    private static final Logger LOG = LoggerFactory.getLogger(ListPeriodicals.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show main view, page={}", request.getParameter(PAGE_PARAM));

        PeriodicalService periodicalServiceImpl = ServiceManager.getInstance().getPeriodicalService();

        int page = 1;

        if (request.getParameter(PAGE_PARAM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAM));
        }

        List<Periodical> periodicals = periodicalServiceImpl.getPerPage(page, ITEMS_PER_PAGE);

        long totalPages = (long) Math.ceil(((double) periodicalServiceImpl.getCount() / ITEMS_PER_PAGE));

        request.setAttribute(PERIODICALS_ATTR, periodicals);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("activePage", page);

        return new NextPage("admin/periodicals.jsp", "FORWARD");

    }
}
