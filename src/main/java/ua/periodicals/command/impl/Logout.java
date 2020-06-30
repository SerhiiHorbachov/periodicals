package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;

import static ua.periodicals.util.Pages.LOGIN_PATH;

public class Logout implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(Logout.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to logout");
        request.getSession().invalidate();
        return new NextPage(LOGIN_PATH, DispatchType.REDIRECT);
    }
}
