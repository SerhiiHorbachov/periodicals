package ua.periodicals.dao.impl;

import ua.periodicals.dao.AbstractInvoiceDao;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao extends AbstractInvoiceDao {

    public static final String FIND_ALL_INVOICES_QUERY =
            "SELECT * FROM invoices";
    public static final String CREATE_INVOICE_QUERY =
            "INSERT INTO invoices (user_id, status, creation_date) VALUES (?, ?, now())";
    public static final String FIND_INVOICE_BY_ID_QUERY =
            "SELECT * FROM invoices WHERE invoice_id = ?";
    public static final String UPDATE_INVOICE_STATUS_QUERY =
            "UPDATE invoices SET status = ? , update_date = now() WHERE invoice_id = ?";
    public static final String DELETE_INVOICE_BY_ID_QUERY =
            "DELETE FROM invoices WHERE invoice_id = ?";

    @Override
    public List<Invoice> findAll() throws DaoException {
        List<Invoice> invoices = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_INVOICES_QUERY);

            while (resultSet.next()) {
                Long invoiceId = resultSet.getLong("invoice_id");
                Long userId = resultSet.getLong("user_id");
                String status = resultSet.getString("status");
                Timestamp createdAt = resultSet.getTimestamp("creation_date");
                Timestamp updatedAt = resultSet.getTimestamp("update_date");

                Invoice tempInvoice = new Invoice(invoiceId, userId, Invoice.STATUS.valueOf(status.toUpperCase()), createdAt, updatedAt);
                invoices.add(tempInvoice);
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to get all invoices from database. ", e);
        }

        return invoices;
    }

    @Override
    public Invoice findById(Long id) throws DaoException {
        Invoice invoice = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_INVOICE_BY_ID_QUERY)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Long invoiceId = resultSet.getLong("invoice_id");
                Long userId = resultSet.getLong("user_id");
                String status = resultSet.getString("status");
                Timestamp createdAt = resultSet.getTimestamp("creation_date");
                Timestamp updatedAt = resultSet.getTimestamp("update_date");

                invoice = new Invoice(invoiceId, userId, Invoice.STATUS.valueOf(status.toUpperCase()), createdAt, updatedAt);
            }

        } catch (SQLException e) {
            throw new DaoException("Couldn't find invoice with id=" + id, e);
        }

        return invoice;
    }

    @Override
    public boolean create(Invoice invoice) throws DaoException {
        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(CREATE_INVOICE_QUERY)) {
            statement.setLong(1, invoice.getUserId());
            statement.setString(2, invoice.getStatus().toString().toUpperCase());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Failed to save new invoice. ", e);
        }

        return result > 0;
    }

    @Override
    public boolean update(Invoice invoice) throws DaoException {
        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_INVOICE_STATUS_QUERY)) {
            statement.setString(1, invoice.getStatus().toString().toUpperCase());
            statement.setLong(2, invoice.getId());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Failed to update invoice, invoiceId=" + invoice.getId(), e);
        }

        return result > 0;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(DELETE_INVOICE_BY_ID_QUERY)) {
            statement.setLong(1, id);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Failed to delete invoice. UserID=" + id, e);
        }

        return result > 0;
    }


}
