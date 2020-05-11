package ua.periodicals.command.impl.admin;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Invoice;
import ua.periodicals.service.impl.InvoiceLogic;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class InvoiceApprove implements ActionCommand {
    @Override
    public NextPage execute(HttpServletRequest request) {
        System.out.println("[INFO] >>InvoiceApprove:" + request.getParameter("id"));

        Long invoiceId = Long.parseLong(request.getParameter("id"));
        InvoiceLogic invoiceLogic = new InvoiceLogic();

        Invoice invoice = invoiceLogic.findById(invoiceId);
        System.out.println("Invoice: " + invoice);

        invoiceLogic.updateStatus(invoiceId, Invoice.STATUS.COMPLETED);

        return new NextPage("/admin/invoices/view?id=" + invoiceId, "REDIRECT");
    }
}
