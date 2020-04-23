package ua.periodicals.command.impl;

import org.mindrot.jbcrypt.BCrypt;
import ua.periodicals.command.Command;
import ua.periodicals.command.NextPageData;
import ua.periodicals.command.util.ResponseType;
import ua.periodicals.service.UserLogic;
import ua.periodicals.model.User;
import ua.periodicals.resource.ConfigurationManager;
import ua.periodicals.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;


public class CreateUserCommand implements Command {
    @Override
    public NextPageData execute(HttpServletRequest request) {

        String page = null;

        String firstName = request.getParameter("firstName").trim();
        String lastName = request.getParameter("lastName").trim();
        String email = request.getParameter("email").trim().toLowerCase();
        String password = request.getParameter("password").trim();
        String role = User.ROLE.USER.toString();

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(firstName, lastName, role, email, hashed);

        boolean isCreated = UserLogic.create(user);

        if (isCreated) {
            page = ConfigurationManager.getProperty("path.page.registration_success");
            return new NextPageData(page, ResponseType.FORWARD);
        } else {
            page = ConfigurationManager.getProperty("path.page.path.page.register");
            request.setAttribute("errorRegisterPassMessage",
                    MessageManager.getProperty("message.registrationerror"));
            return new NextPageData(page, ResponseType.FORWARD);
        }

    }
}
