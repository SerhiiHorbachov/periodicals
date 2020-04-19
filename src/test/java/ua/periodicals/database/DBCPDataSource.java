package ua.periodicals.database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static BasicDataSource ds = new BasicDataSource();

    private static final String DB_URL = "jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;";
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";
    private static final String DB_LOGIN = "sa";
    private static final String DB_PASS = "";

//    DB_CLOSE_DELAY=-1

    static {

        ds.setUrl(DB_URL);
        ds.setDriverClassName(DRIVER_CLASS_NAME);
        ds.setUsername(DB_LOGIN);
        ds.setPassword(DB_PASS);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);

    }

    public static Connection getConnection() throws SQLException {

        return ds.getConnection();
    }

    private DBCPDataSource() {
    }
}
