package ua.periodicals.dao.impl;

import org.junit.jupiter.api.*;
import ua.periodicals.dao.AbstractInvoiceDao;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.database.TestDatabaseManager;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.Invoice;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceDaoTest {

    private static final String TEST_DATABASE_SCRIPT_PATH = "src/test/resources/schema.sql";

    private static TestDatabaseManager testDatabaseManager;
    private static AbstractInvoiceDao invoiceDao;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection;
        testDatabaseManager = new TestDatabaseManager();
        connection = DBCPDataSource.getConnection();
        testDatabaseManager.populateDatabase(TEST_DATABASE_SCRIPT_PATH, connection);
        connection.close();
        invoiceDao = new InvoiceDao();
    }

    @Test
    @Order(1)
    void findAll() throws SQLException {
        long expectedListSize = 11;

        Connection connection = DBCPDataSource.getConnection();
        invoiceDao.setConnection(connection);

        long actualListSize = invoiceDao.findAll().size();

        connection.close();

        assertEquals(expectedListSize, actualListSize);
    }

    @Test
    void create_ShouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            invoiceDao.create(new Invoice());
        });
    }

    @Test
    void findById_ShouldReturnInvoiceObjectWhenInvoiceIdPassed() throws SQLException {
        long invoiceId = 1l;
        long expectedUserId = 2;
        Invoice.STATUS status = Invoice.STATUS.COMPLETED;
        Timestamp createdAt = Timestamp.valueOf("2020-04-10 20:36:56");
        Timestamp updatedAt = Timestamp.valueOf("2020-04-11 9:30:56");

        Invoice expectedInvoice = new Invoice(invoiceId, expectedUserId, status, createdAt, updatedAt);

        Connection connection = DBCPDataSource.getConnection();
        invoiceDao.setConnection(connection);

        Invoice actualInvoice = invoiceDao.findById(invoiceId);

        connection.close();

        assertEquals(expectedInvoice, actualInvoice);
    }

    @Test
    void findById_ShouldReturnNullIfInvoiceNotFound() throws SQLException, DaoException {
        Long invoiceId = 12000L;
        Invoice expectedInvoice = null;

        Connection connection = DBCPDataSource.getConnection();
        invoiceDao.setConnection(connection);

        Invoice actualInvoice = invoiceDao.findById(invoiceId);

        connection.close();

        assertEquals(expectedInvoice, actualInvoice);
    }

    @Test
    @Order(2)
    void submit_ShouldCreateNewInvoiceInDatabaseAndReturnGeneratedId() throws SQLException {
        long expectedGeneratedInvoiceId = 12;
        long expectedUserId = 2;
        Invoice.STATUS status = Invoice.STATUS.IN_PROGRESS;
        Timestamp createdAt = Timestamp.valueOf("2020-05-01 20:00:00");

        Invoice expectedInvoice = new Invoice();
        expectedInvoice.setUserId(expectedUserId);
        expectedInvoice.setStatus(status);
        expectedInvoice.setCreatedAt(createdAt);

        Connection connection = DBCPDataSource.getConnection();
        invoiceDao.setConnection(connection);

        Long actualGeneratedInvoiceId = invoiceDao.submit(expectedInvoice);

        connection.close();

        assertEquals(expectedGeneratedInvoiceId, actualGeneratedInvoiceId);

    }


    @Test
    @Order(3)
    void update_ShouldSetUpdatedAtFieldToCurrentTimeStampAndChangeStatus() throws SQLException {
        Long invoiceId = 11l;
        Invoice.STATUS expectedStatusBeforeUpdate = Invoice.STATUS.IN_PROGRESS;
        Invoice.STATUS expectedStatusAfterUpdate = Invoice.STATUS.COMPLETED;

        Connection connection = DBCPDataSource.getConnection();
        invoiceDao.setConnection(connection);

        //Get invoice
        Invoice invoiceToUpdate = invoiceDao.findById(invoiceId);
        System.out.println(invoiceToUpdate);
        Invoice.STATUS actualStatusBeforeUpdate = invoiceToUpdate.getStatus();
        Timestamp actualUpdatedAtBeforeUpdate = invoiceToUpdate.getUpdatedAt();

        //Update invoice
        invoiceToUpdate.setStatus(Invoice.STATUS.COMPLETED);

        invoiceDao.update(invoiceToUpdate);

        //Check updates
        Invoice invoiceAfterUpdate = invoiceDao.findById(invoiceId);
        Invoice.STATUS actualStatusAfterUpdate = invoiceAfterUpdate.getStatus();
        Timestamp actualUpdatedAtAfterUpdate = invoiceAfterUpdate.getUpdatedAt();

        connection.close();

        assertAll(
            () -> assertEquals(expectedStatusBeforeUpdate, actualStatusBeforeUpdate),
            () -> assertNull(actualUpdatedAtBeforeUpdate),
            () -> assertEquals(expectedStatusAfterUpdate, actualStatusAfterUpdate),
            () -> assertNotNull(actualUpdatedAtAfterUpdate)
        );
    }

    @Test
    @Order(4)
    void deleteById_ShouldRemoveInvoiceFromDatabaseByInvoiceIdAndReturnTrue() throws SQLException {

        long userId = 2l;
        Invoice.STATUS status = Invoice.STATUS.IN_PROGRESS;
        Timestamp createdAt = Timestamp.valueOf("2020-04-10 20:36:56");

        Invoice invoice = new Invoice();
        invoice.setUserId(userId);
        invoice.setStatus(status);
        invoice.setCreatedAt(createdAt);

        Connection connection = DBCPDataSource.getConnection();
        invoiceDao.setConnection(connection);

        long invoiceIdToDelete = invoiceDao.submit(invoice);

        long actualSizeBeforeRemoval = invoiceDao.findAll().size();
        System.out.println("Size: " + actualSizeBeforeRemoval);


        boolean result = invoiceDao.deleteById(invoiceIdToDelete);
        long actualSizeAfterRemoval = invoiceDao.findAll().size();

        connection.close();

        assertAll(
            () -> assertEquals(actualSizeBeforeRemoval, actualSizeAfterRemoval + 1),
            () -> assertTrue(result)
        );

    }

    @Test
    void getInProgress_ShouldReturnListOfInProgressInvoices() throws SQLException {
        long expectedListSize = 4;

        Connection connection = DBCPDataSource.getConnection();
        invoiceDao.setConnection(connection);

        long actualListSize = invoiceDao.getInProgress().size();

        connection.close();

        assertEquals(expectedListSize, actualListSize);
    }

}
