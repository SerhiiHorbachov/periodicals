package ua.periodicals.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    NextPageData execute(HttpServletRequest request);
}
