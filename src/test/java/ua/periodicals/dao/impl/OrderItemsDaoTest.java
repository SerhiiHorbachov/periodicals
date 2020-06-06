package ua.periodicals.dao.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.periodicals.dao.AbstractOrderItemsDao;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.database.TestDatabaseManager;
import ua.periodicals.model.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemsDaoTest {

    private static final String TEST_DATABASE_SCRIPT_PATH = "src/test/resources/schema.sql";

    private static TestDatabaseManager testDatabaseManager;
    private static AbstractOrderItemsDao orderItemsDao;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection;
        testDatabaseManager = new TestDatabaseManager();
        connection = DBCPDataSource.getConnection();
        testDatabaseManager.populateDatabase(TEST_DATABASE_SCRIPT_PATH, connection);
        connection.close();
        orderItemsDao = new OrderItemsDao();
    }

    @Test
    void findAll_ShouldThrowUnsupportedOperationException() {

        assertThrows(UnsupportedOperationException.class, () -> {
            orderItemsDao.findAll();
        });
    }

    @Test
    void create_ShouldReturnTrueWhenOrderItemSavedSuccessfully_WhenNewOrderItemIsPassed() throws SQLException {
        Long invoiceId = 1l;
        Long periodicalId = 1l;
        Long monthlyPriceInCents = 999l;

        OrderItem orderItem = new OrderItem(invoiceId, periodicalId, monthlyPriceInCents);

        Connection connection = DBCPDataSource.getConnection();
        orderItemsDao.setConnection(connection);

        boolean isSaved = orderItemsDao.create(orderItem);

        connection.close();

        assertTrue(isSaved);
    }

    @Test
    void update_ShouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            orderItemsDao.update(new OrderItem());
        });
    }

    @Test
    void deleteById_ShouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            orderItemsDao.deleteById(1l);
        });
    }

    @Test
    void findByInvoiceId_ShouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            orderItemsDao.findById(1l);
        });
    }

    @Test
    void findByInvoiceId_ShouldReturnListOfOrderItemRelatedToTheInvoiceId() throws SQLException {
        Long invoiceId = 2l;
        int expectedSize = 3;

        Connection connection = DBCPDataSource.getConnection();
        orderItemsDao.setConnection(connection);

        List<OrderItem> orderItems = orderItemsDao.findByInvoiceId(invoiceId);

        connection.close();

        assertEquals(expectedSize, orderItems.size());

    }

    @Test
    void getPeriodicalIdsByInvoiceId() throws SQLException {
        Long invoiceId = 2l;
        int expectedSize = 3;

        Connection connection = DBCPDataSource.getConnection();
        orderItemsDao.setConnection(connection);

        List<Long> orderItems = orderItemsDao.getPeriodicalIdsByInvoiceId(invoiceId);

        connection.close();

        assertEquals(expectedSize, orderItems.size());

    }

}
