package ua.periodicals.dao.impl;

import org.junit.jupiter.api.*;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.database.TestDatabaseManager;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest {

    private static final String TEST_DATABASE_SCRIPT_PATH = "src/test/resources/schema.sql";

    private static TestDatabaseManager testDatabaseManager;
    private static UserDao userDao;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection;
        testDatabaseManager = new TestDatabaseManager();
        connection = DBCPDataSource.getConnection();
        testDatabaseManager.populateDatabase(TEST_DATABASE_SCRIPT_PATH, connection);
        connection.close();
        userDao = new UserDao();

    }

    /*Tests for User findByEmailAndPassword(String email, String pwdHash)*/
    @Test
    void findByEmailAndPassword_ShouldReturnUserWithMatchingData() throws SQLException, DaoException {
        User expectedUser = new User(1, "Jack", "Nicholson", "admin", "jack.nich@gmai.com", "1");

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        User actualUser = userDao.findByEmailAndPassword("jack.nich@gmai.com", "1");
        connection.close();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findByEmailAndPassword_ShouldReturnNullIfUserIsNotFound() throws SQLException, DaoException {
        User expectedUser = null;
        String validEmail = "jack.nich@gmai.com";
        String invalidPassword = "6";
        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        User actualUser = userDao.findByEmailAndPassword(validEmail, invalidPassword);
        connection.close();

        assertEquals(expectedUser, actualUser);
    }
    /*end*/
    
    /*Tests for List<User> findAll()*/
    @Test
    @Order(1)
    void findAll_ShouldRetrieveAllUsersFromTableUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        long expectedListSize = 5;

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        long actualListSize = userDao.findAll().size();
        connection.close();

        assertEquals(expectedListSize, actualListSize);
    }
    /*end*/

    /*Tests for boolean create(User user)*/
    @Test
    void create_ShouldIncreaseNumberOfStudentsInTableUserByOne_WhenSavedSuccessfully() throws SQLException, DaoException {
        User userToSave = new User("New", "User", "user", "testemail@mail.co", "test");
        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);

        int numberOfSavedUsersBeforeSaving = userDao.findAll().size();

        userDao.create(userToSave);

        int numberOfSavedUsersAfterSaving = userDao.findAll().size();

        connection.close();

        assertEquals(numberOfSavedUsersBeforeSaving, numberOfSavedUsersAfterSaving - 1);
    }

    @Test
    void create_ShouldThrowDaoException_WhenUserToBeSavedDoesntHaveRole() throws SQLException, DaoException {
        User userToSave = new User();
        userToSave.setFirstName("John");
        userToSave.setLastName("Adams");
        userToSave.setEmail("testemail@mail.co");
        userToSave.setPasswordHash("test");

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);

        connection.close();

        assertThrows(DaoException.class, () -> {
            userDao.create(userToSave);
        });
    }
    /*end*/

    /*Tests for boolean User findById(Long id)*/
    @Test
    void findById_ShouldReturnUserWithMatchingId() throws SQLException, DaoException {
        Long userId = 1L;
        User expectedUser = new User(userId, "Jack", "Nicholson", "admin", "jack.nich@gmai.com", "1");

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        User actualUser = userDao.findById(userId);
        connection.close();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findById_ShouldReturnNullIfUserIsNotFound() throws SQLException, DaoException {
        Long userId = 12000L;
        User expectedUser = null;

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        User actualUser = userDao.findById(userId);
        connection.close();

        assertEquals(expectedUser, actualUser);
    }


    /*end*/


}