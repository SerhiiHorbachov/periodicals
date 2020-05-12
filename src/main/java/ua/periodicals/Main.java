package ua.periodicals;


import ua.periodicals.dao.impl.OrderItemsDao;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.model.OrderItem;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.PeriodicalLogicImpl;
import ua.periodicals.service.impl.UserLogicImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Period;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        PeriodicalLogicImpl periodicalLogic = new PeriodicalLogicImpl();
//        Connection connection = DBCPDataSource.getConnection();

        List<Periodical> periodicals = periodicalLogic.getPerPage(5, 7);

        System.out.println(periodicals);
        System.out.println(periodicalLogic.getCount());

    }
}
