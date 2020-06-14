package ua.periodicals.command.impl.admin;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Invoice;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.impl.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InvoicesView implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {
        System.out.println("[INFO]: >>InvoicesView");

        InvoiceService invoiceLogic = ServiceManager.getInstance().getInvoiceService();

        List<Invoice> inProgressInvoices = invoiceLogic.getInProgress();
        System.out.println(inProgressInvoices);
        request.setAttribute("inProgressInvoices", inProgressInvoices);

        return new NextPage("admin/invoices.jsp", "FORWARD");
    }
}
