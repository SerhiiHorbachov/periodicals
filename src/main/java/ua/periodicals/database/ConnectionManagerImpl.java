package ua.periodicals.database;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager{
    @Override
    public Connection getConnection() throws SQLException {
        return DBCPDataSource.getConnection();
    }
}
