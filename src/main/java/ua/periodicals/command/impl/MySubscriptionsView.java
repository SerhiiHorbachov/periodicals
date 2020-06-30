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

import static ua.periodicals.util.AttributeNames.SUBSCRIPTIONS_ATTR;
import static ua.periodicals.util.AttributeNames.USER_ATTR;
import static ua.periodicals.util.Pages.MY_SUBSCRIPTIONS_PAGE;

public class MySubscriptionsView implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(MySubscriptionsView.class);

    UserService userService;
    
    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show subscriptions view");

        userService = ServiceManager.getInstance().getUserService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTR);

        List<Periodical> subscriptions = userService.getActiveSubscriptions(user.getId());
        request.setAttribute(SUBSCRIPTIONS_ATTR, subscriptions);

        return new NextPage(MY_SUBSCRIPTIONS_PAGE, DispatchType.FORWARD);
    }
}
