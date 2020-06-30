package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;

import static ua.periodicals.util.Pages.LOGIN_PAGE;

public class LoginView implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(LoginView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show login view");
        return new NextPage(LOGIN_PAGE, DispatchType.FORWARD);
    }
}
