package ua.periodicals.command.impl.admin;

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
import static ua.periodicals.util.Pages.ADMIN_PERIODICALS_PAGE;

public class ListPeriodicals implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ListPeriodicals.class);

    private static final int ITEMS_PER_PAGE = 10;

    PeriodicalService periodicalService;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show main view, page={}", request.getParameter(PAGE_PARAM));

        periodicalService = ServiceManager.getInstance().getPeriodicalService();

        int page = 1;

        if (request.getParameter(PAGE_PARAM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAM));
        }

        List<Periodical> periodicals = periodicalService.getPerPage(page, ITEMS_PER_PAGE);

        long totalPages = (long) Math.ceil(((double) periodicalService.getCount() / ITEMS_PER_PAGE));

        request.setAttribute(PERIODICALS_ATTR, periodicals);
        request.setAttribute(TOTAL_PAGES_ATTR, totalPages);
        request.setAttribute(ACTIVE_PAGE_ATTR, page);

        return new NextPage(ADMIN_PERIODICALS_PAGE, DispatchType.FORWARD);

    }
}
