package ua.periodicals.command;

import ua.periodicals.command.impl.EmptyCommand;
import ua.periodicals.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    public Command defineCommand(HttpServletRequest request) {
        Command current = new EmptyCommand();

        // извлечение имени команды из запроса
        String action = request.getParameter("command");

        if (action == null || action.isEmpty()) {
            // если команда не задана в текущем запросе
            return current;
        }

        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongaction"));
        }


        return current;
    }
}
