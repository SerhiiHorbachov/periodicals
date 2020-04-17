package ua.periodicals.command.impl.navigation;

import ua.periodicals.command.Command;
import ua.periodicals.command.NextPageData;
import ua.periodicals.command.util.ResponseType;
import ua.periodicals.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToLoginCommand implements Command {
    @Override
    public NextPageData execute(HttpServletRequest request) {
        String page = null;

        page = ConfigurationManager.getProperty("path.page.login");

        return new NextPageData(page, ResponseType.FORWARD);
    }
}
