package ua.periodicals;

import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.DBCPDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
        Connection conn = DBCPDataSource.getConnection();

        userDao.setConnection(conn);

        System.out.println(userDao.isSubscribedToPeriodical(21, 3));
    }
}
