package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.exception.LogicException;
import ua.periodicals.model.Cart;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.InvoiceLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SubmitInvoice implements ActionCommand {
    @Override
    public NextPage execute(HttpServletRequest request) {
        System.out.println("[INFO]: >>SubmitInvoice");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        InvoiceLogic invoiceLogic = new InvoiceLogic();


        invoiceLogic.submit(user.getId(), cart);

        System.out.println("[INFO]: >>SubmitInvoice - invoice submitted");

        session.removeAttribute("cart");
        String message = "Thank you, your invoice has been submitted and will be processed in the shortest time.";
        request.setAttribute("submit_success", message);


        return new NextPage("invoice-success.jsp", "FORWARD");
    }
}
