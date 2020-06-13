package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;
import ua.periodicals.service.UserService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class Unsubscribe implements ActionCommand {
    private static final String USER_ATTR = "user";
    private static final String SUBSCRIBTIONS_ATTR = "subscriptions";
    private static final String PERIODICAL_ID_PARAM = "periodicalId";

    private static final Logger LOG = LoggerFactory.getLogger(Unsubscribe.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to unsubscribe periodical from user, periodical id=[{}]: ", request.getParameter(PERIODICAL_ID_PARAM));

        UserService userLogic = ServiceManager.getInstance().getUserService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTR);

        userLogic.removePeriodicalFromActiveSubscriptions(user.getId(), Long.parseLong(request.getParameter(PERIODICAL_ID_PARAM)));

        List<Periodical> subscriptions = userLogic.getActiveSubscriptions(user.getId());

        request.setAttribute(SUBSCRIBTIONS_ATTR, subscriptions);

        return new NextPage("my_subscriptions.jsp", "FORWARD");
    }
}
