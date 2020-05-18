package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.UserLogicImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class Unsubscribe implements ActionCommand {
    private static final String USER_ATTR = "user";

    private static final Logger LOG = LoggerFactory.getLogger(Unsubscribe.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to unsubscribe periodical from user, periodical id=[{}]: ", request.getParameter("periodicalId"));

        UserLogicImpl userLogic = new UserLogicImpl();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTR);

        userLogic.removePeriodicalFromActiveSubscriptions(user.getId(), Long.parseLong(request.getParameter("periodicalId")));

        List<Periodical> subscriptions = userLogic.getActiveSubscriptions(user.getId());

        request.setAttribute("subscriptions", subscriptions);

        return new NextPage("my_subscriptions.jsp", "FORWARD");
    }
}
