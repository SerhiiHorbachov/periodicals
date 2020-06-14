package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;

import javax.servlet.http.HttpServletRequest;

public class LoginView implements ActionCommand {
    @Override
    public NextPage execute(HttpServletRequest request) {
        return new NextPage("login.jsp", "FORWARD");
    }
}
