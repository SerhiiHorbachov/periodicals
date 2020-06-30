package ua.periodicals.service.impl;

import org.junit.jupiter.api.*;

import ua.periodicals.database.ConnectionManagerImpl;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.database.TestDatabaseManager;
import ua.periodicals.exception.LogicException;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Invoice;
import ua.periodicals.service.InvoiceService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceServiceImplTest {

    private static final String TEST_DATABASE_SCRIPT_PATH = "src/test/resources/schema.sql";

    private static InvoiceService invoiceService;

    private static TestDatabaseManager testDatabaseManager;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection;
        testDatabaseManager = new TestDatabaseManager();
        connection = DBCPDataSource.getConnection();
        testDatabaseManager.populateDatabase(TEST_DATABASE_SCRIPT_PATH, connection);
        connection.close();

        invoiceService = new InvoiceServiceImpl(new ConnectionManagerImpl());

    }

    @Test
    @Order(2)
    void submit_ShouldReturnTrueWhenInvoiceSubmittedSuccessfully() throws SQLException {
        long userId = 1l;

        Cart cart = new Cart();
        assertTrue(invoiceService.submit(userId, cart));
    }

    @Test
    void submit_ShouldReturnFalseWhenInvoiceSubmittedSuccessfully() throws SQLException {
        long invalidUserId = 547832l;

        Cart cart = new Cart();
        assertThrows(LogicException.class,
            () -> invoiceService.submit(invalidUserId, cart));
    }

    @Test
    @Order(1)
    void getInProgress_ShouldReturnInvoicesWithStatusInProgress() {
        int expectedSize = 4;
        List<Invoice> actualInvoices = invoiceService.getInProgress();

        assertEquals(expectedSize, actualInvoices.size());
    }

    @Test
    void getInvoiceCart_ShouldReturnCartAssignedToInvoice() {

        int expectedCartSize = 2;
        long expectedTotalCost = 1998;

        long invoiceId = 1;
        Cart cart = invoiceService.getInvoiceCart(invoiceId);

        assertAll(
            () -> assertEquals(expectedCartSize, cart.getTotalCount()),
            () -> assertEquals(expectedTotalCost, cart.getTotalCost())
        );
    }

    @Test
    void findById_ShouldReturnInvoiceWithMatchingId() {

        long expectedInvoiceId = 1;
        long expectedUserId = 2;
        Invoice.STATUS expectedStatus = Invoice.STATUS.COMPLETED;

        Invoice actualInvoice = invoiceService.findById(expectedInvoiceId);

        assertAll(
            () -> assertEquals(expectedInvoiceId, actualInvoice.getId()),
            () -> assertEquals(expectedUserId, actualInvoice.getUserId()),
            () -> assertEquals(expectedStatus, actualInvoice.getStatus())
        );

    }

    @Test
    @Order(3)
    void updateStatus_ShouldChangeStatusOfInvoiceToCompleted() {
        long invoiceId = 8;

        Invoice invoiceToBeUpdated = invoiceService.findById(invoiceId);
        Invoice.STATUS expectedStatusBeforeUpdate = invoiceToBeUpdated.getStatus();
        Invoice.STATUS expectedStatusAfterUpdate = Invoice.STATUS.COMPLETED;

        invoiceService.updateStatus(invoiceId, Invoice.STATUS.COMPLETED);

        Invoice invoiceAfterUpdate = invoiceService.findById(invoiceId);

        assertAll(
            () -> assertEquals(invoiceId, invoiceToBeUpdated.getId()),
            () -> assertEquals(expectedStatusBeforeUpdate, Invoice.STATUS.IN_PROGRESS),
            () -> assertEquals(expectedStatusAfterUpdate, invoiceAfterUpdate.getStatus())
        );

    }

}
