package ua.periodicals.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.dao.AbstractOrderItemsDao;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.OrderItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsDao extends AbstractOrderItemsDao {

    private final static String CREATE_ITEM_QUERY = "INSERT \n" +
        "INTO\n" +
        "    order_items\n" +
        "    (invoice_id, periodicals_id, price_per_month) \n" +
        "VALUES\n" +
        "    (?, ?, ?)";

    private final static String FIND_BY__INVOICE_ID_QUERY = "SELECT * FROM order_items WHERE invoice_id=?";
    private final static String FIND_PERIODICAL_IDS_BY_INVOICE_ID = "select periodicals_id from order_items WHERE invoice_id=?";

    private static final Logger LOG = LoggerFactory.getLogger(OrderItemsDao.class);

    @Override
    public List<OrderItem> findAll() throws DaoException {
        LOG.debug("Try to find all invoices");
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(OrderItem orderItem) throws DaoException {
        LOG.debug("Try to create orderItem: {}", orderItem);

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(CREATE_ITEM_QUERY)) {
            statement.setLong(1, orderItem.getInvoiceId());
            statement.setLong(2, orderItem.getPeriodicalId());
            statement.setLong(3, orderItem.getCostPerMonth());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Failed to create orderItem: {}", orderItem);
            throw new DaoException(e);
        }

        return result > 0;
    }

    @Override
    public boolean update(OrderItem orderItem) throws DaoException {
        LOG.debug("Try to update order item: {}", orderItem);
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        LOG.debug("Try to delete order item by id={}", id);
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderItem findById(Long id) throws DaoException {
        LOG.debug("Try to find order item by id={}", id);
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrderItem> findByInvoiceId(Long id) throws DaoException {
        LOG.debug("Try to find order items by invoice id={}", id);

        List<OrderItem> orderItems = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY__INVOICE_ID_QUERY)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long invoice_id = resultSet.getLong("invoice_id");
                Long periodicals_id = resultSet.getLong("periodicals_id");
                Long price_per_month = resultSet.getLong("price_per_month");

                OrderItem orderItem = new OrderItem(invoice_id, periodicals_id, price_per_month);
                orderItems.add(orderItem);
                System.out.println("OrderItem retrieved: " + orderItem);
            }

        } catch (SQLException e) {
            LOG.error("Failed to get all order items by invoice id={}", id);
            throw new DaoException("Failed to get all order items from database. ", e);
        }

        return orderItems;
    }

    public List<Long> getPeriodicalIdsByInvoiceId(long invoiceId) {
        LOG.debug("Try to get periodicals ids by invoice id={}", invoiceId);

        List<Long> ids = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_PERIODICAL_IDS_BY_INVOICE_ID)) {

            statement.setLong(1, invoiceId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("periodicals_id");
                ids.add(id);
            }

        } catch (SQLException e) {
            LOG.error("Failed to get periodical ids by invoice id={}");
            throw new DaoException("Failed to get periodical ids by invoice id=" + invoiceId, e);
        }

        return ids;

    }


}
