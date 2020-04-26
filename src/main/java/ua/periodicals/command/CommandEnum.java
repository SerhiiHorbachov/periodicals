package ua.periodicals.command;

import ua.periodicals.command.impl.LoginCommand;
import ua.periodicals.command.impl.LogoutCommand;
import ua.periodicals.command.impl.CreateUserCommand;


public enum CommandEnum {


    CREATE_USER {
        {
            this.command = new CreateUserCommand();
        }
    },

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },

    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };

    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
