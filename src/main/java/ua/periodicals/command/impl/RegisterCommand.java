package ua.periodicals.command.impl;

import org.mindrot.jbcrypt.BCrypt;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.UserLogicImpl;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {
        UserLogicImpl userLogic = new UserLogicImpl();

        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(User.ROLE.USER.toString());
        user.setEmail(email);
        user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));

        try {
            userLogic.create(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new NextPage("registrationSuccess.jsp", "FORWARD");
    }

}
