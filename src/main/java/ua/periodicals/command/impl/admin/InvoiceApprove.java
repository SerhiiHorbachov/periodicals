package ua.periodicals.command.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Invoice;
import ua.periodicals.service.impl.InvoiceLogic;

import javax.servlet.http.HttpServletRequest;

public class InvoiceApprove implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceApprove.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to approve invoice, id={}", request.getParameter("id"));

        Long invoiceId = Long.parseLong(request.getParameter("id"));
        InvoiceLogic invoiceLogic = new InvoiceLogic();
        
        invoiceLogic.updateStatus(invoiceId, Invoice.STATUS.COMPLETED);

        return new NextPage("/admin/invoices/view?id=" + invoiceId, "REDIRECT");
    }
}
