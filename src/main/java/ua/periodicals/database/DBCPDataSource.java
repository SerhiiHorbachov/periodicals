package ua.periodicals.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBCPDataSource {

    private static final Logger LOG = LoggerFactory.getLogger(DBCPDataSource.class);

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ResourceBundle appProperties = ResourceBundle.getBundle("application");

        ds.setUrl(appProperties.getString("url"));
        ds.setDriverClassName(appProperties.getString("driver_class_name"));
        ds.setUsername(appProperties.getString("username"));
        ds.setPassword(appProperties.getString("password"));
        ds.setMinIdle(Integer.parseInt(appProperties.getString("min_idle")));
        ds.setMaxIdle(Integer.parseInt(appProperties.getString("max_idle")));
    }

    public static Connection getConnection() throws SQLException {
        LOG.debug("Try to get connection");
        return ds.getConnection();
    }

    private DBCPDataSource() {
    }
}
