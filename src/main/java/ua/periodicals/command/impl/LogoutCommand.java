package ua.periodicals.command.impl;

import ua.periodicals.command.Command;
import ua.periodicals.command.NextPageData;
import ua.periodicals.command.util.ResponseType;
import ua.periodicals.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public NextPageData execute(HttpServletRequest request) {
        NextPageData nextPageData = null;
        String page = ConfigurationManager.getProperty("path.page.index");
        // уничтожение сессии
        request.getSession().invalidate();
        return new NextPageData(page, ResponseType.REDIRECT);
    }
}
