package ua.periodicals;


import ua.periodicals.dao.impl.OrderItemsDao;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.model.OrderItem;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.UserLogicImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        OrderItemsDao orderItemsDao = new OrderItemsDao();
        Connection connection = DBCPDataSource.getConnection();
        orderItemsDao.setConnection(connection);
        OrderItem orderItem = new OrderItem(18l, 3l, 999L);


    }
}
