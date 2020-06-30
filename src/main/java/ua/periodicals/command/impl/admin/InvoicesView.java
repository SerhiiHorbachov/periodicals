package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Invoice;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ua.periodicals.util.AttributeNames.IN_PROGRESS_INVOICES_ATTR;
import static ua.periodicals.util.Pages.ADMIN_INVOICES_PAGE;

public class InvoicesView implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(InvoicesView.class);

    InvoiceService invoiceService;

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to show invoices view");

        invoiceService = ServiceManager.getInstance().getInvoiceService();

        List<Invoice> inProgressInvoices = invoiceService.getInProgress();
        request.setAttribute(IN_PROGRESS_INVOICES_ATTR, inProgressInvoices);

        return new NextPage(ADMIN_INVOICES_PAGE, DispatchType.FORWARD);
    }
}
