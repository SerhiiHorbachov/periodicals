package ua.periodicals.command.impl.admin;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;

import javax.servlet.http.HttpServletRequest;

public class GoToCreateNewPeriodicalCommand implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {

        return new NextPage("admin/newPeriodical.jsp", "FORWARD");

    }
}
