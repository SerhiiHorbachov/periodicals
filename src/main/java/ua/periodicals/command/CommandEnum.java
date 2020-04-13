package ua.periodicals.command;

import ua.periodicals.command.impl.LoginCommand;
import ua.periodicals.command.impl.LogoutCommand;
//import ua.periodicals.command.impl.RegisterCommand;

public enum CommandEnum {
//    REGISTER {
//        {
//            this.command = new RegisterCommand();
//        }
//    },

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
