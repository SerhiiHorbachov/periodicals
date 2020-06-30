package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;

import static ua.periodicals.util.Pages.REGISTER_PAGE;

public class RegisterView implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show register view");
        return new NextPage(REGISTER_PAGE, DispatchType.FORWARD);
    }
}
