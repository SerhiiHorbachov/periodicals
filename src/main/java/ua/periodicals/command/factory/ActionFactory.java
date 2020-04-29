package ua.periodicals.command.factory;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.impl.LoginViewCommand;
import ua.periodicals.command.impl.RegisterCommand;
import ua.periodicals.command.impl.admin.GoToCreateNewPeriodicalCommand;
import ua.periodicals.command.impl.admin.ListPeriodicals;
import ua.periodicals.command.impl.RegisterViewCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private Map<String, ActionCommand> actions;

    public ActionFactory() {
        actions = new HashMap<>();

        actions.put("GET/admin/periodicals", new ListPeriodicals());
        actions.put("GET/admin/new-periodical", new GoToCreateNewPeriodicalCommand());
        actions.put("GET/register", new RegisterViewCommand());
        actions.put("POST/register", new RegisterCommand());
        actions.put("GET/login", new LoginViewCommand());


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
