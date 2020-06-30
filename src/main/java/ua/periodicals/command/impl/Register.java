package ua.periodicals.command.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.exception.DuplicateException;
import ua.periodicals.model.User;
import ua.periodicals.service.UserService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;

import static ua.periodicals.util.AttributeNames.*;
import static ua.periodicals.util.Pages.REGISTER_PAGE;
import static ua.periodicals.util.Pages.REGISTRATION_SUCCESS_PAGE;

public class Register implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(Register.class);

    private final static String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{8,20})";
    private final static String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    UserService userService;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to register new user: fname=[{}], lname=[{}], email=[{}].",
            request.getParameter(FIRST_NAME_ATTR),
            request.getParameter(LAST_NAME_ATTR),
            request.getParameter(EMAIL_ATTR)
        );

        userService = ServiceManager.getInstance().getUserService();
        NextPage next = new NextPage();
        boolean isSaved = false;

        String firstName = request.getParameter(FIRST_NAME_ATTR);
        String lastName = request.getParameter(LAST_NAME_ATTR);
        String email = request.getParameter(EMAIL_ATTR);
        String password = request.getParameter(PWD_TTR);

        if (!password.matches(PASSWORD_PATTERN)) {

            next.setPage(REGISTER_PAGE);
            next.setDispatchType(DispatchType.FORWARD);

            String message = String.format("Your password doesn't match the requirements");
            request.setAttribute(INVALID_PWD_PATTERN_ATTR, message);

        } else if (!email.matches(EMAIL_PATTERN)) {

            next.setPage(REGISTER_PAGE);
            next.setDispatchType(DispatchType.FORWARD);

            String message = String.format("Invalid email");
            request.setAttribute(INVALID_EMAIL_PATTERN_ATTR, message);

        } else {

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserRole(User.Role.USER);
            user.setEmail(email);
            user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));

            try {
                isSaved = userService.create(user);
            } catch (DuplicateException e) {
                next.setPage(REGISTER_PAGE);
                next.setDispatchType(DispatchType.FORWARD);

                String message = String.format("User %s already exists.", email);
                request.setAttribute(EMAIL_ALREADY_TAKEN_ATTR, message);

            }
        }

        if (isSaved) {
            next.setPage(REGISTRATION_SUCCESS_PAGE);
            next.setDispatchType(DispatchType.FORWARD);
        }

        return next;
    }

}
