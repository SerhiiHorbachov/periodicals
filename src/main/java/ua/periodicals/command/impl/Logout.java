package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;

import javax.servlet.http.HttpServletRequest;

public class Logout implements ActionCommand {
    @Override
    public NextPage execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new NextPage("/login", "REDIRECT");

    }
}
