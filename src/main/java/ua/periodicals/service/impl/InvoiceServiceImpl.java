package ua.periodicals.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.dao.AbstractPeriodicalDao;
import ua.periodicals.dao.EntityTransaction;
import ua.periodicals.dao.impl.InvoiceDao;
import ua.periodicals.dao.impl.OrderItemsDao;
import ua.periodicals.dao.impl.PeriodicalDao;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.ConnectionManager;
import ua.periodicals.exception.DaoException;
import ua.periodicals.exception.LogicException;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Invoice;
import ua.periodicals.model.OrderItem;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.InvoiceService;

import java.util.List;

/**
 * The purpose of the class is to manage operations related to Invoice.
 * Package access. New instance should be created in @see ServiceManager
 * Database Connection manager should be injected via constructor.
 *
 * @author Serhii Hor
 */
class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final ConnectionManager connectionManager;

    private InvoiceDao invoiceDao;
    private OrderItemsDao orderItemsDao;
    private UserDao userDao;

    /**
     * Constructor.
     *
     * @param connectionManager provider for database connection.
     */
    public InvoiceServiceImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.invoiceDao = new InvoiceDao();
        this.orderItemsDao = new OrderItemsDao();
        this.userDao = new UserDao();
    }

    /**
     * @param userId user id.
     * @param cart   card object that contains order items.
     * @return boolean
     * @throws LogicException
     */
    @Override
    public boolean submit(long userId, Cart cart) {
        LOG.debug("Try to submit invoice, userId={}, cart: {}", userId, cart);

        boolean result;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        Invoice invoice = new Invoice(userId);

        try {
            transaction.begin(invoiceDao, orderItemsDao);

            long invoiceId = invoiceDao.submit(invoice);

            for (Periodical periodical : cart.getCartItems()) {
                OrderItem orderItem = new OrderItem(invoiceId, periodical.getId(), (long) periodical.getMonthlyPrice());
                orderItemsDao.create(orderItem);
            }

            result = true;
            transaction.commit();

        } catch (DaoException e) {
            LOG.error("Failed to submit invoice, userid={}, cart: {}", userId, cart, e);
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {

            try {
                transaction.end();
            } catch (DaoException e) {
                LOG.error("Failed To back the transaction.", e);
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return result;
    }

    /**
     * Finds all invoices with status 'IN_PROGRESS'
     *
     * @return List<Invoice>
     * @throws LogicException
     */
    @Override
    public List<Invoice> getInProgress() {

        List<Invoice> invoices;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

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
                LOG.error("Rolling back the transaction.", e);
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return invoices;
    }

    /**
     * Restores a cart that contains Periodicals related to the Invoice
     *
     * @param invoiceId invoice id
     * @return Cart contains Periodicals related to the Invoice
     * @throws LogicException
     */
    @Override
    public Cart getInvoiceCart(Long invoiceId) {
        LOG.debug("Try to restore cart related to invoice id={}", invoiceId);

        List<OrderItem> orderItems = null;
        Cart cart = new Cart();

        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(orderItemsDao, periodicalDao);
            orderItems = orderItemsDao.findByInvoiceId(invoiceId);

            for (OrderItem orderItem : orderItems) {
                Periodical invoicePeriodical = periodicalDao.findById(orderItem.getPeriodicalId());
                cart.addItem(invoicePeriodical);
            }

            transaction.commit();
        } catch (DaoException e) {
            LOG.error("Failed to get invoice cart.", e);
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {

            try {
                transaction.end();
            } catch (DaoException e) {

                LOG.error("Failed to end transaction.", e);
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);

            }
        }

        return cart;
    }

    /**
     * Finds Invoice by its Id.
     *
     * @param id
     * @return Invocie
     * @throws LogicException
     */
    @Override
    public Invoice findById(Long id) {
        LOG.debug("Try to find invoice by id={}", id);

        Invoice invoice;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(invoiceDao);
            invoice = invoiceDao.findById(id);
            transaction.commit();
        } catch (DaoException e) {

            LOG.error("Failed to find by id.", e);
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);

        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {

                LOG.error("Failed to end transaction.", e);
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);

            }
        }

        return invoice;
    }

    /**
     * Updates status of the invoice.
     * In case status us changed to "COMPLETED", periodicals are assigned to the user.
     * In case status us changed to "CANCELLED", only invoice status is changed.
     *
     * @param invoiceId
     * @param status
     * @return boolean
     * @throws LogicException
     */
    @Override
    public boolean updateStatus(long invoiceId, Invoice.STATUS status) {
        LOG.debug("Try to update invoice status: invoiceId" + invoiceId + ", status: " + status.toString());

        boolean result = false;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(invoiceDao, userDao, orderItemsDao);

            Invoice invoice = invoiceDao.findById(invoiceId);

            invoice.setStatus(status);

            if (status.equals(Invoice.STATUS.COMPLETED)) {
                List<Long> periodicalIds = orderItemsDao.getPeriodicalIdsByInvoiceId(invoice.getId());

                for (Long id : periodicalIds) {
                    userDao.addSubscription(invoice.getUserId(), id);
                }
            }

            result = invoiceDao.update(invoice);

            transaction.commit();
        } catch (DaoException e) {

            LOG.error("Failed to do update invoice status: ", e);
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);

        } finally {

            try {
                transaction.end();
            } catch (DaoException e) {

                LOG.error("Failed to end transaction.", e);
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);

            }

        }

        return result;
    }

}
