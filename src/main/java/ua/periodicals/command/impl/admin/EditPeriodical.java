package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.exception.ValidationException;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class EditPeriodical implements ActionCommand {

    private static final String ERROR_BUNDLE = "error_messages";
    private static final String PERIODICAL_VALIDATION_MSG_KEY = "msg.periodical_validation";
    private static final String PERIODICAL_NAME_PARAM = "name";
    private static final String PERIODICAL_ID_PARAM = "periodicalId";
    private static final String PERIODICAL_PRICE_PARAM = "price";
    private static final String PERIODICAL_DESCRIPTION_PARAM = "description";
    private static final String PERIODICAL_VALIDATION_MESSAGE_ATTR = "validationMsg";

    private static final Logger LOG = LoggerFactory.getLogger(EditPeriodicalView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to edit periodical, id=[{}], name=[{}], price=[{}], description=[{}]",
            request.getParameter(PERIODICAL_ID_PARAM),
            request.getParameter(PERIODICAL_NAME_PARAM),
            request.getParameter(PERIODICAL_PRICE_PARAM),
            request.getParameter(PERIODICAL_DESCRIPTION_PARAM));

        ResourceBundle errorResourceBundle = ResourceBundle.getBundle(ERROR_BUNDLE);
        NextPage next = new NextPage();
        PeriodicalService periodicalLogic = ServiceManager.getInstance().getPeriodicalService();


        if (request.getParameter(PERIODICAL_PRICE_PARAM).isEmpty() ||
            request.getParameter(PERIODICAL_NAME_PARAM).isEmpty()) {
            LOG.info("Initial param validation failed, returning message to user.");

            request.setAttribute(PERIODICAL_VALIDATION_MESSAGE_ATTR, errorResourceBundle.getString(PERIODICAL_VALIDATION_MSG_KEY));
            next.setPage("admin/editPeriodical.jsp");
            next.setDispatchType("FORWARD");

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

                periodicalLogic.update(periodical);
            } catch (ValidationException e) {
                request.setAttribute(PERIODICAL_VALIDATION_MESSAGE_ATTR, errorResourceBundle.getString(PERIODICAL_VALIDATION_MSG_KEY));
                next.setPage("admin/editPeriodical.jsp");
                next.setDispatchType("FORWARD");
            }

            next.setPage("/admin/periodicals");
            next.setDispatchType("REDIRECT");

        }

        return next;

    }
}
