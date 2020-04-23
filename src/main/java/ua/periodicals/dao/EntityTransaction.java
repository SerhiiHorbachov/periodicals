package ua.periodicals.dao;

import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private Connection connection;

    public EntityTransaction() {

    }

    public void begin(AbstractDao dao, AbstractDao... daos) {

        if (connection == null) {
            try {
                connection = DBCPDataSource.getConnection();
            } catch (SQLException e) {
                throw new DaoException("Database connection wasn't established. ", e);
            }
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

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Failed to commit transaction. ", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("Failed to commit transaction. ", e);
        }
    }

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