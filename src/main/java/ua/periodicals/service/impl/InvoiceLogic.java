package ua.periodicals.service.impl;

import ua.periodicals.dao.AbstractInvoiceDao;
import ua.periodicals.dao.AbstractPeriodicalDao;
import ua.periodicals.dao.EntityTransaction;
import ua.periodicals.dao.impl.InvoiceDao;
import ua.periodicals.dao.impl.OrderItemsDao;
import ua.periodicals.dao.impl.PeriodicalDao;
import ua.periodicals.exception.DaoException;
import ua.periodicals.exception.LogicException;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Invoice;
import ua.periodicals.model.OrderItem;
import ua.periodicals.model.Periodical;

import java.util.List;


public class InvoiceLogic {

    public boolean submit(long userId, Cart cart) {
        System.out.println(">>InvoiceLogic submit");
        boolean result = false;
        EntityTransaction transaction = new EntityTransaction();
        InvoiceDao invoiceDao = new InvoiceDao();

        OrderItemsDao orderItemsDao = new OrderItemsDao();


        Invoice invoice = new Invoice(userId);

        try {
            transaction.begin(invoiceDao, orderItemsDao);

            long invoiceId = invoiceDao.submit(invoice);
            System.out.println("[INFO] NewInvoice Created with id=" + invoiceId);

            for (Periodical periodical : cart.getCartItems()) {
                OrderItem orderItem = new OrderItem(invoiceId, periodical.getId(), (long) periodical.getMonthlyPrice());

                System.out.println(orderItem);
                orderItemsDao.create(orderItem);

                System.out.println("Order Item saved: " + orderItem.toString());

            }

            System.out.println("[INFO] orderItems saved");


            result = true;
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return result;
    }

    public List<Invoice> getInProgress() {

        List<Invoice> invoices;

        AbstractInvoiceDao invoiceDao = new InvoiceDao();
        EntityTransaction transaction = new EntityTransaction();

        try {
            transaction.begin(invoiceDao);
            invoices = invoiceDao.getInProgress();
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return invoices;
    }

    public Cart getInvoiceCart(Long invoiceId) {

        System.out.println(">>getInvoiceCart(), invoiceId=" + invoiceId);
        List<OrderItem> orderItems = null;
        Cart cart = new Cart();

        OrderItemsDao orderItemDao = new OrderItemsDao();
        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

        try {
            transaction.begin(orderItemDao, periodicalDao);
            orderItems = orderItemDao.findByInvoiceId(invoiceId);

            for (OrderItem orderItem : orderItems) {
                Periodical invoicePeriodical = periodicalDao.findById(orderItem.getPeriodicalId());
                //Set Periodical price of order items price
                cart.addItem(invoicePeriodical);

            }

            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return cart;
    }

    public Invoice findById(Long id) {
        Invoice invoice = new Invoice();

        AbstractInvoiceDao invoiceDao = new InvoiceDao();
        EntityTransaction transaction = new EntityTransaction();

        try {
            transaction.begin(invoiceDao);
            invoice = invoiceDao.findById(id);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return invoice;
    }

    public boolean updateStatus(long invoiceId, Invoice.STATUS status) {
        System.out.println(">>updateStatus: invoiceId" + invoiceId + ", status: " + status.toString());

        boolean result = false;

        EntityTransaction transaction = new EntityTransaction();
        InvoiceDao invoiceDao = new InvoiceDao();


        try {
            transaction.begin(invoiceDao);

            Invoice invoice = invoiceDao.findById(invoiceId);

            invoice.setStatus(status);
            System.out.println("[INFO] Invoice to update: " + invoice);

            result = invoiceDao.update(invoice);
            
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return result;
    }


}
