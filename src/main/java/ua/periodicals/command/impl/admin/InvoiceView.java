package ua.periodicals.command.impl.admin;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Invoice;
import ua.periodicals.model.User;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.UserService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;

public class InvoiceView implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {
        System.out.println("[INFO]: >>InvoiceEdit");

        Long invoiceId = Long.parseLong(request.getParameter("id"));

        InvoiceService invoiceLogic = ServiceManager.getInstance().getInvoiceService();

        Invoice invoice = invoiceLogic.findById(invoiceId);
        request.setAttribute("invoice", invoice);

        Cart cart = invoiceLogic.getInvoiceCart(invoiceId);
        request.setAttribute("cart", cart);

        UserService userLogic = ServiceManager.getInstance().getUserService();
        User user = userLogic.findById(invoice.getUserId());
        request.setAttribute("user", user);

        return new NextPage("admin/invoice.jsp", "FORWARD");
    }
}
