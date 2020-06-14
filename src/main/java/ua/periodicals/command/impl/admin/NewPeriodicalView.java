package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;

import javax.servlet.http.HttpServletRequest;

public class NewPeriodicalView implements ActionCommand {

    private static final Logger LOG = LoggerFactory.getLogger(NewPeriodicalView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to forward to add new periodical view");
        return new NextPage("admin/newPeriodical.jsp", "FORWARD");
    }
}
