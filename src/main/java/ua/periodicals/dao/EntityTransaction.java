package ua.periodicals.dao;

import ua.periodicals.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class manages transactions
 *
 * @author Serhii Hor
 */
public class EntityTransaction {

    private Connection connection;

    /**
     * Constructor.
     *
     * @param connection user for the transaction lifecycle.
     */
    public EntityTransaction(Connection connection) {
        this.connection = connection;
    }

    /**
     * Sets Autocommit to false
     *
     * @param dao  single DAO object
     * @param daos add other DAOs if multiple DAOs should use the same transaction
     * @throws DaoException
     */
    public void begin(AbstractDao dao, AbstractDao... daos) {

        if (connection == null) {
            throw new DaoException("Database connection wasn't established.");
        }

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Failed setting autocommit 'false' for transaction. ", e);
        }

        dao.setConnection(connection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    /**
     * Commits transaction.
     *
     * @throws DaoException
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Failed to commit transaction. ", e);
        }
    }

    /**
     * Transaction rollback.
     *
     * @throws DaoException
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("Failed to commit transaction. ", e);
        }
    }

    /**
     * Finishes the transaction.
     * Sets autocommit back to true and closes connection.
     *
     * @throws DaoException
     */
    public void end() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException("Failed to set autocommit to 'true'. ", e);
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Failed to close connection. ", e);
        }

    }

}
