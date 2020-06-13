package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.exception.AuthenticationException;
import ua.periodicals.exception.InvalidPasswordException;
import ua.periodicals.model.User;
import ua.periodicals.service.UserService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements ActionCommand {
    private static final String ERROR_BUNDLE = "error_messages";
    private static final String INVALID_EMAIL_MSG_KEY = "msg.email_not_exist";
    private static final String INVALID_PASSWORD_MSG_KEY = "msg.invalid_pwd";

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String AUTHENTICATION_ERR_MSG_PARAM = "authenticationErrorMessage";

    private static final String LANGUAGE_SESSION_ATTR = "lang";
    private static final String ROLE_ATTR = "role";
    private static final String USER_ATTR = "user";

    private static final Logger LOG = LoggerFactory.getLogger(Login.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to Login: email: {}", request.getParameter(EMAIL_PARAM));

        HttpSession session = request.getSession();

        Locale locale = new Locale(session.getAttribute(LANGUAGE_SESSION_ATTR).toString());
        ResourceBundle resourceBundle = ResourceBundle.getBundle(ERROR_BUNDLE, locale);

        NextPage next = new NextPage();

        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();

        UserService userLogic = ServiceManager.getInstance().getUserService();
        User user = null;

        try {
            user = userLogic.authenticate(email, password);
        } catch (AuthenticationException e) {
            LOG.info("Authentication failed, email: {}", email);

            next.setPage("login.jsp");
            next.setDispatchType("FORWARD");

            String message = String.format(resourceBundle.getString(INVALID_EMAIL_MSG_KEY), email);
            request.setAttribute(AUTHENTICATION_ERR_MSG_PARAM, message);

        } catch (InvalidPasswordException e) {
            LOG.info("Invalid password: {}", email);

            next.setPage("login.jsp");
            next.setDispatchType("FORWARD");

            String message = resourceBundle.getString(INVALID_PASSWORD_MSG_KEY);
            request.setAttribute(AUTHENTICATION_ERR_MSG_PARAM, message);

        }

        if (user != null) {

            switch (user.getUserRole()) {
                case ADMIN:
                    next.setPage("/admin/periodicals");
                    next.setDispatchType("REDIRECT");
                    break;
                case USER:
                    next.setPage("/main");
                    next.setDispatchType("REDIRECT");
                    break;
                default:
                    next.setPage("login.jsp");
                    next.setDispatchType("FORWARD");
            }

            String role = user.getUserRole().toString();

            session.setAttribute(USER_ATTR, user);
            session.setAttribute(ROLE_ATTR, role);

        }

        return next;

    }
}
