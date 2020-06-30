package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;

import static ua.periodicals.util.AttributeNames.PERIODICAL_ID_PARAM;
import static ua.periodicals.util.Pages.ADMIN_PERIODICALS_PATH;

public class DeletePeriodical implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(DeletePeriodical.class);

    PeriodicalService periodicalLogic;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to delete periodical, id={}", request.getParameter(PERIODICAL_ID_PARAM));

        periodicalLogic = ServiceManager.getInstance().getPeriodicalService();
        periodicalLogic.delete(Long.parseLong(request.getParameter(PERIODICAL_ID_PARAM)));

        NextPage next = new NextPage();

        next.setPage(ADMIN_PERIODICALS_PATH);
        next.setDispatchType(DispatchType.REDIRECT);

        return next;
    }


}
