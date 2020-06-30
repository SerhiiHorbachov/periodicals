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

import static ua.periodicals.util.AttributeNames.ID_ATTR;
import static ua.periodicals.util.AttributeNames.PERIODICAL_ATTR;
import static ua.periodicals.util.Pages.ADMIN_EDIT_PERIODICAL_PAGE;

public class EditPeriodicalView implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(EditPeriodicalView.class);

    PeriodicalService periodicalServiceImpl;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show edit periodical view, periodicalId={}", request.getParameter("id"));

        periodicalServiceImpl = ServiceManager.getInstance().getPeriodicalService();

        Long id = Long.parseLong(request.getParameter(ID_ATTR));
        Periodical periodical = periodicalServiceImpl.findById(id);

        request.setAttribute(PERIODICAL_ATTR, periodical);

        return new NextPage(ADMIN_EDIT_PERIODICAL_PAGE, DispatchType.FORWARD);
    }
}
