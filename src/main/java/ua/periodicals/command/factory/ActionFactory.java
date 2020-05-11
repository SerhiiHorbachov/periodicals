package ua.periodicals.command.factory;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.impl.*;
import ua.periodicals.command.impl.admin.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private Map<String, ActionCommand> actions;

    public ActionFactory() {
        actions = new HashMap<>();

        actions.put("GET/main", new MainView());
        actions.put("GET/my/cart", new CartView());
        actions.put("POST/my/cart/remove", new RemoveFromCart());
        actions.put("POST/my/cart/submit-invoice", new SubmitInvoice());
        actions.put("POST/main/add-to-cart", new AddToCart());
        actions.put("GET/admin/periodicals", new ListPeriodicals());
        actions.put("GET/admin/invoices/in_progress", new InvoicesView());
        actions.put("GET/admin/invoices/view", new InvoiceView());
        actions.put("POST/admin/invoices/approve", new InvoiceApprove());
        actions.put("POST/admin/invoices/cancel", new InvoiceCancel());
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
        String formActionCmd = null;

        if (request.getMethod().equals("POST") && request.getParameter("command") != null) {

            System.out.println("[INFO] DEFINING POST CMD: " + request.getParameter("command"));
            formActionCmd = "/" + request.getParameter("command");

        }

        String action = request.getMethod() + request.getServletPath();
        if (formActionCmd != null) {
            action = action + formActionCmd;
        }

        //Remove!
        System.out.println("Action: " + action);
        command = actions.get(action);

        return command;
    }
}
