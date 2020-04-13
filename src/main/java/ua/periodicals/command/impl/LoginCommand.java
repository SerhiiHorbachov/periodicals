package ua.periodicals.command.impl;

import ua.periodicals.command.Command;
import ua.periodicals.command.NextPageData;
import ua.periodicals.command.util.ResponseType;
import ua.periodicals.logic.PeriodicalLogic;
import ua.periodicals.logic.UserLogic;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;
import ua.periodicals.resource.ConfigurationManager;
import ua.periodicals.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public NextPageData execute(HttpServletRequest request) {

        String page = null;
        User user = null;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        user = UserLogic.findByEmailAndPwdHash(login, pass);

        if (user == null) {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");

            return new NextPageData(page, ResponseType.FORWARD);
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("role", user.getRole());

        // get all periodicals and pass to request
        List<Periodical> periodicals = PeriodicalLogic.findAll();
        request.setAttribute("periodical_list", periodicals);

        if (user.getRole().toUpperCase().equals(User.ROLE.ADMIN.toString())) {
            page = ConfigurationManager.getProperty("path.page.admin");
        } else {
            page = ConfigurationManager.getProperty("path.page.main");
        }

        return new NextPageData(page, ResponseType.FORWARD);

    }
}
