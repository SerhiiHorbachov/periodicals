package ua.periodicals.command;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
    NextPage execute(HttpServletRequest request);
}
