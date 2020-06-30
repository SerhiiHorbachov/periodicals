package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;
import ua.periodicals.service.UserService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static ua.periodicals.util.AttributeNames.*;
import static ua.periodicals.util.Pages.MY_SUBSCRIPTIONS_PAGE;

public class Unsubscribe implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(Unsubscribe.class);

    UserService userService;

    public Unsubscribe() {
        this.userService = ServiceManager.getInstance().getUserService();
    }

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to unsubscribe periodical from user, periodical id=[{}]: ", request.getParameter(PERIODICAL_ID_PARAM));
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTR);

        userService.removePeriodicalFromActiveSubscriptions(user.getId(), Long.parseLong(request.getParameter(PERIODICAL_ID_PARAM)));

        List<Periodical> subscriptions = userService.getActiveSubscriptions(user.getId());

        request.setAttribute(SUBSCRIPTIONS_ATTR, subscriptions);

        return new NextPage(MY_SUBSCRIPTIONS_PAGE, DispatchType.FORWARD);
    }
}
