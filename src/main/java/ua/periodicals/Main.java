package ua.periodicals;

import org.mindrot.jbcrypt.BCrypt;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String password = "hello world";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashed);

        UserDao userDao = new UserDao();
        Connection connection = DBCPDataSource.getConnection();
        userDao.setConnection(connection);
        System.out.println(userDao.findByEmailAndPassword("jack.nich@gmai.com", "3"));


    }
}
