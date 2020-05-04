package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;

import javax.servlet.http.HttpServletRequest;

public class RegisterView implements ActionCommand {
    @Override
    public NextPage execute(HttpServletRequest request) {
        return new NextPage("register.jsp", "FORWARD");
    }
}
