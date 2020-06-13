package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.model.User;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SubmitInvoice implements ActionCommand {

    private static final Logger LOG = LoggerFactory.getLogger(SubmitInvoice.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show successful invoice submission view");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        InvoiceService invoiceLogic = ServiceManager.getInstance().getInvoiceService();

        invoiceLogic.submit(user.getId(), cart);

        session.removeAttribute("cart");

        return new NextPage("invoice-success.jsp", "FORWARD");
    }
}
