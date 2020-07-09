package ua.periodicals.database;

import ua.periodicals.exception.DatabaseConnectionException;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection() throws DatabaseConnectionException;
}
