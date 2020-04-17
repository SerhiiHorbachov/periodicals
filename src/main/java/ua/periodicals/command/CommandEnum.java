package ua.periodicals.command;

import ua.periodicals.command.impl.LoginCommand;
import ua.periodicals.command.impl.LogoutCommand;
import ua.periodicals.command.impl.CreateUserCommand;
import ua.periodicals.command.impl.navigation.GoToLoginCommand;
import ua.periodicals.command.impl.navigation.GoToRegisterCommand;

public enum CommandEnum {


    GO_TO_LOGIN {
        {
            this.command = new GoToLoginCommand();
        }
    },

    GO_TO_REGISTER {
        {
            this.command = new GoToRegisterCommand();
        }
    },

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
