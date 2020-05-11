package ua.periodicals.dao;

import ua.periodicals.exception.DaoException;
import ua.periodicals.model.OrderItem;

import java.util.List;

public abstract class AbstractOrderItemsDao extends AbstractDao<OrderItem> {
    public abstract List<OrderItem> findByInvoiceId(Long id) throws DaoException;
}
