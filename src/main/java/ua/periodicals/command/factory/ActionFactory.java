package ua.periodicals.command.factory;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.impl.*;
import ua.periodicals.command.impl.admin.GoToCreateNewPeriodicalCommand;
import ua.periodicals.command.impl.admin.ListPeriodicals;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private Map<String, ActionCommand> actions;

    public ActionFactory() {
        actions = new HashMap<>();

        actions.put("GET/main", new MainView());
        actions.put("GET/admin/periodicals", new ListPeriodicals());
        actions.put("GET/admin/new-periodical", new GoToCreateNewPeriodicalCommand());
        actions.put("GET/register", new RegisterView());
        actions.put("POST/register", new Register());
        actions.put("GET/login", new LoginView());
        actions.put("POST/login", new Login());
        actions.put("GET/logout", new Logout());

    }

    public ActionCommand defineCommand(HttpServletRequest request) {
        //Remove!
        System.out.println("--Define Command");
        ActionCommand command = null;

        String action = request.getMethod() + request.getServletPath();

        //Remove!
        System.out.println("Action: " + action);
        command = actions.get(action);

        return command;
    }
}
