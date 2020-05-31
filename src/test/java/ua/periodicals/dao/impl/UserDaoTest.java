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

    @Test
    void findById_ShouldReturnUserWithMatchingId() throws SQLException, DaoException {
        Long userId = 1L;
        User expectedUser = new User(userId, "Jack", "Nicholson", User.Role.ADMIN, "jack.nich@gmai.com", "1");

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

    @Test
    void findByEmailAndPassword_ShouldReturnUserWithMatchingData() throws SQLException, DaoException {
        User expectedUser = new User(1, "Jack", "Nicholson", User.Role.ADMIN, "jack.nich@gmai.com", "1");

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

    @Test
    void findByEmailShouldReturnUser() throws SQLException {

        String validEmail = "jack.nich@gmai.com";
        User expectedUser = new User(1, "Jack", "Nicholson", User.Role.ADMIN, validEmail, "1");

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        User actualUser = userDao.findByEmail(validEmail);
        connection.close();
        assertEquals(expectedUser, actualUser);

    }

    @Test
    void create_ShouldIncreaseNumberOfStudentsInTableUserByOne_WhenSavedSuccessfully() throws SQLException, DaoException {
        User userToSave = new User("New", "User", User.Role.USER, "testemail@mail.co", "test");
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

    @Test
    void update_ShouldSaveChangedDataInDatabase() throws SQLException {
        Long userId = 5l;
        String expectedChangedFirstName = "Changed Name";
        String expectedChangedEmail = "changed@mail.co";

        User userToUpdate = new User(userId, expectedChangedFirstName, "Pachino", User.Role.ADMIN, expectedChangedEmail, "5");
        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        userDao.update(userToUpdate);

        User actualUpdatedUser = userDao.findById(userId);
        connection.close();

        assertEquals(userToUpdate, actualUpdatedUser);
    }

    @Test
    void deleteById_ShouldRemoveUserWithIndicatedIdFromDatabase() throws SQLException {
        Long userIdToDelete = 4l;

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);

        long numberOfUsersBeforeRemoving = userDao.findAll().size();

        userDao.deleteById(userIdToDelete);

        long numberOfUsersAfterRemoving = userDao.findAll().size();
        connection.close();

        assertEquals(numberOfUsersBeforeRemoving, numberOfUsersAfterRemoving + 1);
    }

    @Test
    void deleteById_ShouldReturnFalse_WhenPassedIdIsNotPresentInDatabase() throws SQLException {
        long invalidId = 12000;

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);

        assertFalse(userDao.deleteById(invalidId));
        connection.close();

    }

    @Order(2)
    @Test
    void getActiveSubscriptionsIds_ShouldReturnListOfSubscribedPeriodicalIdsByUserId() throws SQLException {

        Long userId = 2l;
        int expectedSize = 10;

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);

        List<Long> actualPeriodicalIds = userDao.getActiveSubscriptionsIds(userId);
        assertEquals(actualPeriodicalIds.size(), expectedSize);

        connection.close();

    }

    @Test
    void removeSubscription_ShouldDeleteRowFromTable() throws SQLException {
        long userId = 2l;
        long periodicalsId = 11l;

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);

        boolean isSubscribedBeforeRemoval = userDao.isSubscribedToPeriodical(userId, periodicalsId);
        boolean isUnsubscribedSuccessfully = userDao.removeSubscription(userId, periodicalsId);
        boolean isSubscribedAfterRemoval = userDao.isSubscribedToPeriodical(userId, periodicalsId);

        connection.close();

        assertAll(
            () -> assertTrue(isSubscribedBeforeRemoval),
            () -> assertTrue(isUnsubscribedSuccessfully),
            () -> assertFalse(isSubscribedAfterRemoval)
        );
    }

    @Test
    void isSubscribedToPeriodical_ShouldReturnTrue_WhenUserIsSubscribedToPeriodicals() throws SQLException {
        long userId = 2l;
        long periodicalsId = 10;

        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        boolean isSubscribed = userDao.isSubscribedToPeriodical(userId, periodicalsId);
        connection.close();

        assertTrue(isSubscribed);
    }

}