package ua.periodicals.dao.impl;

import org.junit.jupiter.api.*;
import ua.periodicals.dao.AbstractPeriodicalDao;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.database.TestDatabaseManager;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.Periodical;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PeriodicalDaoTest {

    private static final String TEST_DATABASE_SCRIPT_PATH = "src/test/resources/schema.sql";

    private static TestDatabaseManager testDatabaseManager;
    private static AbstractPeriodicalDao periodicalDao;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection;
        testDatabaseManager = new TestDatabaseManager();
        connection = DBCPDataSource.getConnection();
        testDatabaseManager.populateDatabase(TEST_DATABASE_SCRIPT_PATH, connection);
        connection.close();
        periodicalDao = new PeriodicalDao();
    }

    /*Tests for List<User> findAll()*/
    @Disabled
    @Test
    @Order(1)
    void findAll_ShouldRetrievePeriodicalsFromTablePeriodicals() throws SQLException {
        List<Periodical> periodicals = new ArrayList<>();
        long expectedListSize = 23;

        Connection connection = DBCPDataSource.getConnection();
        periodicalDao.setConnection(connection);
        long actualListSize = periodicalDao.findAll().size();

        connection.close();

        assertEquals(expectedListSize, actualListSize);
    }


    /*end*/

    /*Tests for Periodical findById(Long id)*/
    @Test
    void findById_ShouldReturnPeriodicalWithMatchingId() throws SQLException, DaoException {
        Long periodicalId = 1L;
        String name = "Game Informer";
        String description = "";
        int monthly_price = 999;

        Periodical expectedPeriodical = new Periodical(periodicalId, name, description, monthly_price);

        Connection connection = DBCPDataSource.getConnection();
        periodicalDao.setConnection(connection);
        Periodical actualPeriodical = periodicalDao.findById(periodicalId);
        connection.close();

        assertEquals(expectedPeriodical, actualPeriodical);
    }

    @Test
    void findById_ShouldReturnNullIfPeriodicalIsNotFound() throws SQLException, DaoException {
        Long periodicalId = 12000L;
        Periodical expectedPeriodical = null;

        Connection connection = DBCPDataSource.getConnection();
        periodicalDao.setConnection(connection);
        Periodical actualPeriodical = periodicalDao.findById(periodicalId);
        connection.close();

        assertEquals(expectedPeriodical, actualPeriodical);
    }
    /*end*/

    /*Tests for boolean create(User user)*/
    @Test
    void create_ShouldIncreaseNumberOfRowsInTablePeriodicalsByOne_WhenSavedSuccessfully() throws SQLException, DaoException {
        Long periodicalId = 1L;
        String name = "NewTestPeriodical";
        String description = "lorem ipsum";
        int monthly_price = 10000;

        Periodical newPeriodical = new Periodical(periodicalId, name, description, monthly_price);
        Connection connection = DBCPDataSource.getConnection();
        periodicalDao.setConnection(connection);

        int numberOfSavedUsersBeforeSaving = periodicalDao.findAll().size();

        periodicalDao.create(newPeriodical);

        int numberOfSavedUsersAfterSaving = periodicalDao.findAll().size();

        connection.close();

        assertEquals(numberOfSavedUsersBeforeSaving, numberOfSavedUsersAfterSaving - 1);
    }

    @Test
    void create_ShouldThrowDaoException_WhenPeriodicalToBeSavedDoesntHaveName() throws SQLException, DaoException {
        String description = "lorem ipsum";
        int monthly_price = 10000;

        Periodical newPeriodicalToFailBeSaved = new Periodical();
        newPeriodicalToFailBeSaved.setDescription(description);
        newPeriodicalToFailBeSaved.setMonthlyPrice(monthly_price);

        Connection connection = DBCPDataSource.getConnection();
        periodicalDao.setConnection(connection);

        connection.close();

        assertThrows(DaoException.class, () -> {
            periodicalDao.create(newPeriodicalToFailBeSaved);
        });
    }
    /*end*/

    /*Tests for boolean update(Periodical periodical)*/
    @Test
    void update_ShouldSetNewValues_WhenUpdatedPeriodicPassed() throws SQLException, DaoException {
        Long periodicalId = 10L;
        String expectedChangedName = "Changed Name";
        String expectedChangedDescription = "Changed description";
        int changedMonthly_price = 30000;

        Periodical periodicalToUpdate = new Periodical(periodicalId, expectedChangedName, expectedChangedDescription, changedMonthly_price);
        Connection connection = DBCPDataSource.getConnection();
        periodicalDao.setConnection(connection);
        periodicalDao.update(periodicalToUpdate);

        Periodical actualUpdatedPeriodical = periodicalDao.findById(periodicalId);
        connection.close();

        assertEquals(periodicalToUpdate, actualUpdatedPeriodical);
    }
    /*end*/

    /*Tests for boolean deleteById(Long id))*/
    @Test
    void deleteById_ShouldRemovePeriodicalWithIndicatedIdFromDatabase() throws SQLException {
        Long periodicalIdToDelete = 23l;

        Connection connection = DBCPDataSource.getConnection();
        periodicalDao.setConnection(connection);

        long numberOfPeriodicalsBeforeRemoving = periodicalDao.findAll().size();

        periodicalDao.deleteById(periodicalIdToDelete);

        long numberOfPeriodicalsAfterRemoving = periodicalDao.findAll().size();
        connection.close();

        assertEquals(numberOfPeriodicalsBeforeRemoving, numberOfPeriodicalsAfterRemoving + 1);
    }

    @Test
    void deleteById_ShouldReturnFalse_WhenPassedIdIsNotPresentInDatabase() throws SQLException {
        long invalidId = 12000;

        Connection connection = DBCPDataSource.getConnection();
        periodicalDao.setConnection(connection);

        assertFalse(periodicalDao.deleteById(invalidId));
    }
    /*end*/

}