package ua.periodicals.command.impl.navigation;

import ua.periodicals.command.Command;
import ua.periodicals.command.NextPageData;
import ua.periodicals.command.util.ResponseType;
import ua.periodicals.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToRegisterCommand implements Command {
    @Override
    public NextPageData execute(HttpServletRequest request) {

        String page = ConfigurationManager.getProperty("path.page.register");
        return new NextPageData(page, ResponseType.FORWARD);
    }
}
