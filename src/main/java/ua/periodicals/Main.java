package ua.periodicals;


import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.DBCPDataSource;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.UserLogicImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserLogicImpl userLogic = new UserLogicImpl();
//
//        String email = "johnlord@gmai.com";
//        String password = "qwerty2";
//        User user = userLogic.authenticate(email, password);
//
//        System.out.println(user.toString());

/*        Connection connection = DBCPDataSource.getConnection();
        UserDao userDao = new UserDao();
        userDao.setConnection(connection);

        List<User> users = userDao.findAll();
        System.out.println(users.toString());
 */
        String pwd = "$2a$10$/cOdResElJydbfAqfPEOuuvSqj3aG/UlVwaLKmfzGPrsaV6Y8Rn7K";
        String fname = "Marty";
        String lname = "McFly";
        String invalidEmail = "adams.j@gmai.com";
        String validEmail = "marty.mcfly";
        User userToSave = new User(fname, lname, User.Role.USER, invalidEmail, pwd);
//        User user = userLogic.findByEmail(validEmail);
        System.out.println(userLogic.create(userToSave));

//        System.out.println(user);


    }
}
