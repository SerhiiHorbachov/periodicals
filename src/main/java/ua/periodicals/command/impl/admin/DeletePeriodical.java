package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.service.impl.PeriodicalLogicImpl;

import javax.servlet.http.HttpServletRequest;

public class DeletePeriodical implements ActionCommand {

    private static final String PERIODICAL_ID_PARAM = "periodicalId";

    private static final Logger LOG = LoggerFactory.getLogger(DeletePeriodical.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to delete periodical, id={}", request.getParameter(PERIODICAL_ID_PARAM));

        PeriodicalLogicImpl periodicalLogic = new PeriodicalLogicImpl();

        periodicalLogic.delete(Long.parseLong(request.getParameter(PERIODICAL_ID_PARAM)));
        
        NextPage next = new NextPage();

        next.setPage("/admin/periodicals");
        next.setDispatchType("REDIRECT");

        return next;
    }


}
