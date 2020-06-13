package ua.periodicals.command.impl.admin;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Invoice;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;

public class InvoiceCancel implements ActionCommand {
    @Override
    public NextPage execute(HttpServletRequest request) {
        System.out.println("[INFO] >>InvoiceCancel:" + request.getParameter("id"));

        Long invoiceId = Long.parseLong(request.getParameter("id"));
        InvoiceService invoiceLogic = ServiceManager.getInstance().getInvoiceService();

        Invoice invoice = invoiceLogic.findById(invoiceId);
        System.out.println("Invoice: " + invoice);

        invoiceLogic.updateStatus(invoiceId, Invoice.STATUS.CANCELLED);

        return new NextPage("/admin/invoices/view?id=" + invoiceId, "REDIRECT");
    }
}
