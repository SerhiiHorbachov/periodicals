package ua.periodicals.dao.impl;

import ua.periodicals.dao.AbstractUserDao;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractUserDao {

    public static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";
    public static final String FIND_USER_BY_EMAIL_AND_PWD_HASH_QUERY =
            "SELECT * FROM users WHERE email=? AND password_hash = ?";
    public static final String FIND_USER_BY_ID_QUERY =
            "SELECT * FROM users WHERE user_id=?";
    public static final String CREATE_USER_QUERY =
            "INSERT INTO users(user_id, first_name, last_name, role, email, password_hash) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER_QUERY =
            "UPDATE users SET first_name=?, last_name=?, role=?, email=?, password_hash=?  WHERE user_id=?";
    public static final String DELETE_USER_BY_ID_QUERY = "DELETE FROM users WHERE user_id=?";

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_QUERY);

            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String role = resultSet.getString("role");
                String email = resultSet.getString("email");
                String passwordHash = resultSet.getString("password_hash");

                User tempUser = new User(userId, firstName, lastName, role, email, passwordHash);
                users.add(tempUser);
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to get all users from database. ", e);
        }

        return users;
    }

    @Override
    public User findById(Long id) throws DaoException {

        User user = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID_QUERY)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Long userId = resultSet.getLong("user_id");
                String fName = resultSet.getString("first_name");
                String lName = resultSet.getString("last_name");
                String storedRole = resultSet.getString("role");
                String storedEmail = resultSet.getString("email");
                String storedPwdHash = resultSet.getString("password_hash");

                user = new User(userId, fName, lName, storedRole, storedEmail, storedPwdHash);
            }

        } catch (SQLException e) {
            throw new DaoException("Couldn't find user with id=" + id, e);
        }

        return user;
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
            throw new DaoException("Failed to save new user. ", e);
        }

        return result > 0;
    }

    @Override
    public boolean update(User user) throws DaoException {

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPasswordHash());
            statement.setLong(6, user.getId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update user. UserID=" + user.getId(), e);
        }

        return result > 0;

    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID_QUERY)) {
            statement.setLong(1, id);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Failed to delete user. UserID=" + id, e);
        }

        return result > 0;
    }

}
