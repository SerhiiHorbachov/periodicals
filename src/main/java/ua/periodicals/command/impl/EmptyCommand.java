package ua.periodicals.command.impl;

import ua.periodicals.command.Command;
import ua.periodicals.command.NextPageData;
import ua.periodicals.command.util.ResponseType;
import ua.periodicals.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {
    @Override
    public NextPageData execute(HttpServletRequest request) {
        NextPageData nextPageData = null;

        /* в случае ошибки или прямого обращения к контроллеру
         * переадресация на страницу ввода логина */
        String page = ConfigurationManager.getProperty("path.page.login");
        nextPageData = new NextPageData(page, ResponseType.REDIRECT);
        return nextPageData;
    }
}
