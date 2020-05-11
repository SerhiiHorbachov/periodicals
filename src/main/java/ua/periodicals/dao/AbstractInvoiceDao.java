package ua.periodicals.dao;


import ua.periodicals.exception.DaoException;
import ua.periodicals.model.Invoice;

import java.util.List;

public abstract class AbstractInvoiceDao extends AbstractDao<Invoice> {
    public abstract long submit(Invoice entity) throws DaoException;

    public abstract List<Invoice> getInProgress() throws DaoException;
}
