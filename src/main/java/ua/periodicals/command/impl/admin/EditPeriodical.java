package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.exception.ValidationException;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

import static ua.periodicals.util.AttributeNames.*;
import static ua.periodicals.util.Pages.ADMIN_EDIT_PERIODICAL_PAGE;
import static ua.periodicals.util.Pages.ADMIN_PERIODICALS_PATH;

public class EditPeriodical implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(EditPeriodicalView.class);

    private static final String ERROR_BUNDLE = "error_messages";
    private static final String PERIODICAL_VALIDATION_MSG_KEY = "msg.periodical_validation";

    PeriodicalService periodicalService;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to edit periodical, id=[{}], name=[{}], price=[{}], description=[{}]",
            request.getParameter(PERIODICAL_ID_PARAM),
            request.getParameter(PERIODICAL_NAME_PARAM),
            request.getParameter(PERIODICAL_PRICE_PARAM),
            request.getParameter(PERIODICAL_DESCRIPTION_PARAM));

        ResourceBundle errorResourceBundle = ResourceBundle.getBundle(ERROR_BUNDLE);
        NextPage next = new NextPage();
        periodicalService = ServiceManager.getInstance().getPeriodicalService();

        if (request.getParameter(PERIODICAL_PRICE_PARAM).isEmpty() ||
            request.getParameter(PERIODICAL_NAME_PARAM).isEmpty()) {
            LOG.warn("Initial param validation failed, returning message to user.");

            request.setAttribute(PERIODICAL_VALIDATION_MESSAGE_ATTR, errorResourceBundle.getString(PERIODICAL_VALIDATION_MSG_KEY));
            next.setPage(ADMIN_EDIT_PERIODICAL_PAGE);
            next.setDispatchType(DispatchType.FORWARD);

        } else {
            LOG.debug("initial validation passed");

            Long id = Long.parseLong(request.getParameter(PERIODICAL_ID_PARAM));
            String name = request.getParameter(PERIODICAL_NAME_PARAM);
            long price = Math.round(Float.parseFloat(request.getParameter(PERIODICAL_PRICE_PARAM)) * 100);
            String description = request.getParameter(PERIODICAL_DESCRIPTION_PARAM);

            Periodical periodical = new Periodical();
            periodical.setId(id);
            periodical.setName(name);
            periodical.setMonthlyPrice(price);
            periodical.setDescription(description);

            try {
                LOG.debug("Updating periodical in database");
                periodicalService.update(periodical);
            } catch (ValidationException e) {
                request.setAttribute(PERIODICAL_VALIDATION_MESSAGE_ATTR, errorResourceBundle.getString(PERIODICAL_VALIDATION_MSG_KEY));
                next.setPage(ADMIN_EDIT_PERIODICAL_PAGE);
                next.setDispatchType(DispatchType.FORWARD);
            }

            next.setPage(ADMIN_PERIODICALS_PATH);
            next.setDispatchType(DispatchType.REDIRECT);

        }

        return next;

    }
}
