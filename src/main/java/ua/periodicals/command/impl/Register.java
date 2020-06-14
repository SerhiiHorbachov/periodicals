package ua.periodicals.command.impl;

import org.mindrot.jbcrypt.BCrypt;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.exception.DuplicateException;
import ua.periodicals.model.User;
import ua.periodicals.service.UserService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;

public class Register implements ActionCommand {
    private final static String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{8,20})";
    private final static String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    @Override
    public NextPage execute(HttpServletRequest request) {
        UserService userLogic = ServiceManager.getInstance().getUserService();
        NextPage next = new NextPage();
        boolean isSaved = false;

        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!password.matches(PASSWORD_PATTERN)) {

            next.setPage("register.jsp");
            next.setDispatchType("FORWARD");

            String message = String.format("Your password doesn't match the requirements");
            request.setAttribute("invalidPasswordPatternMessage", message);

        } else if (!email.matches(EMAIL_PATTERN)) {

            next.setPage("register.jsp");
            next.setDispatchType("FORWARD");

            String message = String.format("Invalid email");
            request.setAttribute("invalidEmailPatternMessage", message);

        } else {

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserRole(User.Role.USER);
            user.setEmail(email);
            user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));

            try {
                isSaved = userLogic.create(user);
            } catch (DuplicateException e) {
                next.setPage("register.jsp");
                next.setDispatchType("FORWARD");

                String message = String.format("User %s already exists.", email);
                request.setAttribute("emailAlreadyTakenMessage", message);

            }
        }

        if (isSaved) {
            next.setPage("registrationSuccess.jsp");
            next.setDispatchType("FORWARD");
        }

        return next;
    }

}
