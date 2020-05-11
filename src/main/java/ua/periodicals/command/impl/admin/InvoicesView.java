package ua.periodicals.command.impl.admin;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Invoice;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.impl.InvoiceLogic;
import ua.periodicals.service.impl.PeriodicalLogicImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InvoicesView implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {
        System.out.println("[INFO]: >>InvoicesView");

        InvoiceLogic invoiceLogic = new InvoiceLogic();

        List<Invoice> inProgressInvoices = invoiceLogic.getInProgress();
        System.out.println(inProgressInvoices);
        request.setAttribute("inProgressInvoices", inProgressInvoices);

        return new NextPage("admin/invoices.jsp", "FORWARD");
    }
}
