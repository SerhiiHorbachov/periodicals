package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.model.User;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static ua.periodicals.util.AttributeNames.CART_ATTR;
import static ua.periodicals.util.AttributeNames.USER_ATTR;
import static ua.periodicals.util.Pages.INVOICE_SUCCESS_PAGE;

public class SubmitInvoice implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(SubmitInvoice.class);

    InvoiceService invoiceService;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show successful invoice submission view");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTR);
        Cart cart = (Cart) session.getAttribute(CART_ATTR);

        invoiceService = ServiceManager.getInstance().getInvoiceService();
        invoiceService.submit(user.getId(), cart);

        session.removeAttribute(CART_ATTR);

        return new NextPage(INVOICE_SUCCESS_PAGE, DispatchType.FORWARD);
    }
}
