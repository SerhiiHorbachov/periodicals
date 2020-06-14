package ua.periodicals.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionManagerImpl.class);

    @Override
    public Connection getConnection() throws DatabaseConnectionException {
        LOG.debug("Try to get connection");

        try {
            return DBCPDataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("Failed getting connection, {}", e);
            throw new DatabaseConnectionException("Failed to get connection.", e);
        }
    }
}
