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

public class MySubscriptionsView implements ActionCommand {
    private static final String USER_ATTR = "user";

    private static final Logger LOG = LoggerFactory.getLogger(MySubscriptionsView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show subscriptions view");

        UserService userLogic = ServiceManager.getInstance().getUserService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTR);

        List<Periodical> subscriptions = userLogic.getActiveSubscriptions(user.getId());

        request.setAttribute("subscriptions", subscriptions);

        return new NextPage("my_subscriptions.jsp", "FORWARD");
    }
}
