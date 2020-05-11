package ua.periodicals.command.impl.admin;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Invoice;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.InvoiceLogic;
import ua.periodicals.service.impl.UserLogicImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InvoiceView implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {
        System.out.println("[INFO]: >>InvoiceEdit");

        Long invoiceId = Long.parseLong(request.getParameter("id"));
        InvoiceLogic invoiceLogic = new InvoiceLogic();

        Invoice invoice = invoiceLogic.findById(invoiceId);
        request.setAttribute("invoice", invoice);

        Cart cart = invoiceLogic.getInvoiceCart(invoiceId);
        request.setAttribute("cart", cart);

        UserLogicImpl userLogic = new UserLogicImpl();
        User user = userLogic.findById(invoice.getUserId());
        request.setAttribute("user", user);

        return new NextPage("admin/invoice.jsp", "FORWARD");
    }
}
