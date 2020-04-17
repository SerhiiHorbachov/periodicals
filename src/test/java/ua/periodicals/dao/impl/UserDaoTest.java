package ua.periodicals.dao.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.database.TestDatabaseManager;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.User;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private static final String TEST_DATABASE_SCRIPT_PATH = "src/test/resources/schema.sql";

    private static TestDatabaseManager testDatabaseManager;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection;
        testDatabaseManager = new TestDatabaseManager();
        connection = DBCPDataSource.getConnection();
        testDatabaseManager.populateDatabase(TEST_DATABASE_SCRIPT_PATH, connection);
        connection.close();

    }

    @Test
    void test() throws SQLException, DaoException {
        Connection connection = DBCPDataSource.getConnection();
        System.out.println(connection.isValid(0));
        UserDao userDao = new UserDao();
        userDao.setConnection(connection);
        User user = userDao.findByEmailAndPassword("jack.nich@gmai.com", "1");
        connection.close();
        System.out.println(user.toString());

        assertTrue(true);

    }

}