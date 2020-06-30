package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ua.periodicals.util.AttributeNames.*;
import static ua.periodicals.util.Pages.MAIN_PAGE;

public class MainView implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(MainView.class);

    private static final int ITEMS_PER_PAGE = 9;

    PeriodicalService periodicalService;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show main view, page={}", request.getParameter(PAGE_PARAM));

        periodicalService = ServiceManager.getInstance().getPeriodicalService();

        int page = 1;

        if (request.getParameter(PAGE_PARAM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAM));
        }

        long totalPages = (long) Math.ceil(((double) periodicalService.getCount() / ITEMS_PER_PAGE));

        List<Periodical> periodicals = periodicalService.getPerPage(page, ITEMS_PER_PAGE);
        request.setAttribute(PERIODICALS_ATTR, periodicals);
        request.setAttribute(TOTAL_PAGES_ATTR, totalPages);
        request.setAttribute(ACTIVE_PAGE_ATTR, page);
        request.setAttribute(INVALID_ID_ATTR, request.getParameter("inv_id"));

        return new NextPage(MAIN_PAGE, DispatchType.FORWARD);
    }
}
