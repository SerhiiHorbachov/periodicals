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
import static ua.periodicals.util.Pages.*;

public class AddPeriodical implements ActionCommand {

    private static final Logger LOG = LoggerFactory.getLogger(AddPeriodical.class);

    private static final String ERROR_BUNDLE = "error_messages";
    private static final String PERIODICAL_VALIDATION_MSG_KEY = "msg.periodical_validation";

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to save new periodical, name={}, price={}, description={}",
            request.getParameter(PERIODICAL_NAME_ATTR),
            request.getParameter(PERIODICAL_PRICE_PARAM),
            request.getParameter(PERIODICAL_DESCRIPTION_PARAM));

        ResourceBundle errorResourceBundle = ResourceBundle.getBundle(ERROR_BUNDLE);
        NextPage next = new NextPage();
        PeriodicalService periodicalLogic = ServiceManager.getInstance().getPeriodicalService();

        if (request.getParameter(PERIODICAL_PRICE_PARAM).isEmpty() || request.getParameter(PERIODICAL_NAME_ATTR).isEmpty()) {
            LOG.info("Initial param validation failed, returning message to user.");

            request.setAttribute(PERIODICAL_VALIDATION_MESSAGE_ATTR, errorResourceBundle.getString(PERIODICAL_VALIDATION_MSG_KEY));
            next.setPage(ADMIN_NEW_PERIODICAL_PAGE);
            next.setDispatchType(DispatchType.FORWARD);

        } else {
            LOG.debug("initial validation passed");

            String name = request.getParameter(PERIODICAL_NAME_ATTR);
            long price = Math.round(Float.parseFloat(request.getParameter(PERIODICAL_PRICE_PARAM)) * 100);
            String description = request.getParameter(PERIODICAL_DESCRIPTION_PARAM);

            Periodical periodical = new Periodical();
            periodical.setName(name);
            periodical.setMonthlyPrice(price);
            periodical.setDescription(description);

            try {
                LOG.debug("Saving periodical in database");

                periodicalLogic.create(periodical);
            } catch (ValidationException e) {
                request.setAttribute(PERIODICAL_VALIDATION_MESSAGE_ATTR, errorResourceBundle.getString(PERIODICAL_VALIDATION_MSG_KEY));
                next.setPage(ADMIN_PERIODICALS_PAGE);
                next.setDispatchType(DispatchType.FORWARD);
            }

            next.setPage(ADMIN_PERIODICALS_PATH);
            next.setDispatchType(DispatchType.REDIRECT);

        }

        return next;
    }

}
