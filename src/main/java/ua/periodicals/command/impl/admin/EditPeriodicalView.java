package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;

public class EditPeriodicalView implements ActionCommand {

    private static final String PERIODICAL_ATTR = "periodical";
    private static final String PERIODICAL_ID_PARAM = "id";

    private static final Logger LOG = LoggerFactory.getLogger(EditPeriodicalView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show edit periodical view, periodicalId={}", request.getParameter("id"));

        PeriodicalService periodicalServiceImpl = ServiceManager.getInstance().getPeriodicalService();

        Long id = Long.parseLong(request.getParameter(PERIODICAL_ID_PARAM));
        Periodical periodical = periodicalServiceImpl.findById(id);

        request.setAttribute(PERIODICAL_ATTR, periodical);

        return new NextPage("admin/editPeriodical.jsp", "FORWARD");
    }
}
