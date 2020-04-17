package ua.periodicals.dao.impl;

import ua.periodicals.dao.AbstractUserDao;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao extends AbstractUserDao {

    public static final String FIND_USER_BY_EMAIL_AND_PWD_HASH_QUERY = "SELECT * FROM users WHERE email=? AND password_hash = ?";
    public static final String CREATE_USER_QUERY = "INSERT INTO users(user_id, first_name, last_name, role, email, password_hash) VALUES (DEFAULT, ?, ?, ?, ?, ?)";

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean create(User user) throws DaoException {
        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPasswordHash());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result > 0;
    }

    @Override
    public User findById(Long id) throws DaoException {

        return null;
    }

    @Override
    public User findByEmailAndPassword(String email, String pwdHash) throws DaoException {
        User user = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PWD_HASH_QUERY)) {
            statement.setString(1, email);
            statement.setString(2, pwdHash);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int userId = resultSet.getInt("user_id");
                String fName = resultSet.getString("first_name");
                String lName = resultSet.getString("last_name");
                String storedRole = resultSet.getString("role");
                String storedEmail = resultSet.getString("email");
                String storedPwdHash = resultSet.getString("password_hash");

                user = new User(userId, fName, lName, storedRole, storedEmail, storedPwdHash);

            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return user;
    }
}
