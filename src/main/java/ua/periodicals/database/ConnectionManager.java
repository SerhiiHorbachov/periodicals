package ua.periodicals.database;

import ua.periodicals.exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {
    Connection getConnection() throws DatabaseConnectionException;
}
