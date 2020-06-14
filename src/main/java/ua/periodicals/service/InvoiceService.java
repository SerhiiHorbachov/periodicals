package ua.periodicals.service;

import ua.periodicals.model.Cart;
import ua.periodicals.model.Invoice;

import java.util.List;

public interface InvoiceService {

    boolean submit(long userId, Cart cart);

    List<Invoice> getInProgress();

    Cart getInvoiceCart(Long invoiceId);

    Invoice findById(Long id);

    boolean updateStatus(long invoiceId, Invoice.STATUS status);
}
