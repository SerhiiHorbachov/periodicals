package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Invoice;
import ua.periodicals.model.User;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.UserService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;

import static ua.periodicals.util.AttributeNames.*;
import static ua.periodicals.util.Pages.ADMIN_INVOICE_PAGE;

public class InvoiceView implements ActionCommand {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceView.class);

    InvoiceService invoiceLogic;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show Invoice view");

        Long invoiceId = Long.parseLong(request.getParameter(ID_ATTR));

        invoiceLogic = ServiceManager.getInstance().getInvoiceService();

        Invoice invoice = invoiceLogic.findById(invoiceId);
        request.setAttribute(INVOICE_ATTR, invoice);

        Cart cart = invoiceLogic.getInvoiceCart(invoiceId);
        request.setAttribute(CART_ATTR, cart);

        UserService userLogic = ServiceManager.getInstance().getUserService();
        User user = userLogic.findById(invoice.getUserId());
        request.setAttribute(USER_ATTR, user);

        return new NextPage(ADMIN_INVOICE_PAGE, DispatchType.FORWARD);
    }
}
