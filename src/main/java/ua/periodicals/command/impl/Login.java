package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.exception.AuthenticationException;
import ua.periodicals.exception.InvalidPasswordException;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.UserLogicImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Login implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {

        NextPage next = new NextPage();

        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        System.out.println(email);
        System.out.println(password);

        UserLogicImpl userLogic = new UserLogicImpl();
        User user = null;

        try {
            user = userLogic.authenticate(email, password);
        } catch (AuthenticationException e) {

            next.setPage("login.jsp");
            next.setDispatchType("FORWARD");

            String message = String.format("User %s is not registered.", email);
            request.setAttribute("authenticationErrorMessage", message);

        } catch (InvalidPasswordException e) {
            next.setPage("login.jsp");
            next.setDispatchType("FORWARD");

            String message = "Invalid password";
            request.setAttribute("authenticationErrorMessage", message);

        }

        if (user != null) {
            System.out.println("ROLE: " + user.getUserRole());

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

            HttpSession session = request.getSession();
            String role = user.getUserRole().toString();

            session.setAttribute("user", user);
            session.setAttribute("role", role);

        }

        System.out.println(next.toString());
        return next;

    }
}
