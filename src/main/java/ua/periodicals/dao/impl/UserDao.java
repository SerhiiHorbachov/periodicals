package ua.periodicals.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static final String FIND_USER_BY_EMAIL_QUERY =
        "SELECT * FROM users WHERE email=?";
    public static final String FIND_USER_BY_ID_QUERY =
        "SELECT * FROM users WHERE user_id=?";
    public static final String CREATE_USER_QUERY =
        "INSERT INTO users(user_id, first_name, last_name, role, email, password_hash) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER_QUERY =
        "UPDATE users SET first_name=?, last_name=?, role=?, email=?, password_hash=?  WHERE user_id=?";
    public static final String DELETE_USER_BY_ID_QUERY = "DELETE FROM users WHERE user_id=?";
    public static final String ADD_SUBSCRIPTION = "INSERT INTO users_periodicals(user_id, periodical_id) VALUES (?, ?)";
    public static final String GET_ACTIVE_SUBSCRIPTIONS_BY_USER_ID_QUERY = "SELECT\n" +
        "    periodical_id \n" +
        "from\n" +
        "    users_periodicals \n" +
        "where\n" +
        "    user_id=?";
    public static final String UNSUBSCRIBE_PERIODICAL_QUERY = "DELETE \n" +
        "FROM\n" +
        "    users_periodicals \n" +
        "WHERE\n" +
        "    user_id = ? \n" +
        "    AND periodical_id = ?";
    public static final String IS_USER_SUBSCRIBED_TO_PERIODICAL_QUERY = "SELECT\n" +
        "    * \n" +
        "FROM\n" +
        "    users_periodicals \n" +
        "WHERE\n" +
        "    user_id=? \n" +
        "    AND periodical_id =?";

    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

    @Override
    public List<User> findAll() {
        LOG.debug("Try to find all users");

        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_QUERY);

            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                User.Role role = User.Role.valueOf(resultSet.getString("role"));
                String email = resultSet.getString("email");
                String passwordHash = resultSet.getString("password_hash");

                User tempUser = new User(userId, firstName, lastName, role, email, passwordHash);
                users.add(tempUser);
            }

        } catch (SQLException e) {
            LOG.error("Failed to find all users");
            throw new DaoException("Failed to get all users from database. ", e);
        }

        return users;
    }

    @Override
    public User findById(Long id) throws DaoException {

        LOG.debug("Try to find user by id={}", id);

        User user = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID_QUERY)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Long userId = resultSet.getLong("user_id");
                String fName = resultSet.getString("first_name");
                String lName = resultSet.getString("last_name");
                User.Role role = User.Role.valueOf(resultSet.getString("role"));
                String storedEmail = resultSet.getString("email");
                String storedPwdHash = resultSet.getString("password_hash");

                user = new User(userId, fName, lName, role, storedEmail, storedPwdHash);
            }

        } catch (SQLException e) {
            LOG.error("Failed to find user by id={}", id);
            throw new DaoException("Couldn't find user with id=" + id, e);
        }

        return user;
    }

    @Override
    public User findByEmailAndPassword(String email, String pwdHash) throws DaoException {
        LOG.debug("Try to find user by email and password: {}", email);

        User user = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PWD_HASH_QUERY)) {
            statement.setString(1, email);
            statement.setString(2, pwdHash);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int userId = resultSet.getInt("user_id");
                String fName = resultSet.getString("first_name");
                String lName = resultSet.getString("last_name");
                User.Role role = User.Role.valueOf(resultSet.getString("role"));
                String storedEmail = resultSet.getString("email");
                String storedPwdHash = resultSet.getString("password_hash");

                user = new User(userId, fName, lName, role, storedEmail, storedPwdHash);

            }

        } catch (SQLException e) {
            LOG.error("Failed to find user by email and pwd, email={}: ", email, e);
            throw new DaoException("Failed to find user by email and pwd", e);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        LOG.debug("Try to find user by email: {}", email);

        User user = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_QUERY)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int userId = resultSet.getInt("user_id");
                String fName = resultSet.getString("first_name");
                String lName = resultSet.getString("last_name");
                User.Role role = User.Role.valueOf(resultSet.getString("role"));
                String storedEmail = resultSet.getString("email");
                String storedPwdHash = resultSet.getString("password_hash");

                user = new User(userId, fName, lName, role, storedEmail, storedPwdHash);
            }

        } catch (SQLException e) {
            LOG.error("Failed to find user by email: {}", email, e);
            throw new DaoException("Failed to find user by email", e);
        }

        return user;
    }

    @Override
    public boolean create(User user) throws DaoException {
        LOG.debug("Try to create user: {}", user);

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserRole().toString());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPasswordHash());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Failed to create user: {}", user, e);
            throw new DaoException("Failed to save new user. ", e);
        }

        return result > 0;
    }

    @Override
    public boolean update(User user) throws DaoException {
        LOG.debug("Try to update user: {}", user);

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserRole().toString());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPasswordHash());
            statement.setLong(6, user.getId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Failed to update user: ", user, e);
            throw new DaoException("Failed to update user. UserID=" + user.getId(), e);
        }

        return result > 0;

    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        LOG.debug("Try to delete user by id: {}", id);

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID_QUERY)) {
            statement.setLong(1, id);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Failed to delete user by id={}", id, e);
            throw new DaoException("Failed to delete user. UserID=" + id, e);
        }

        return result > 0;
    }

    @Override
    public boolean addSubscription(long userId, long periodicalId) {
        LOG.debug("Try to add subscription: userId={}, periodicalId={}", userId, periodicalId);
        int result;

        try (PreparedStatement statement = connection.prepareStatement(ADD_SUBSCRIPTION)) {
            statement.setLong(1, userId);
            statement.setLong(2, periodicalId);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Failed to add subscription to user: userID={}, periodicalId={}", userId, periodicalId, e);
            throw new DaoException(String.format("Failed to add subscription to userId=%d,  periodicalId=%d", userId, periodicalId), e);
        }

        return result > 0;

    }

    @Override
    public List<Long> getActiveSubscriptionsIds(long userId) {
        LOG.debug("Try to get active subscriptions ids for userid={}", userId);
        List<Long> subscriptionIds = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ACTIVE_SUBSCRIPTIONS_BY_USER_ID_QUERY)) {
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("periodical_id");
                subscriptionIds.add(id);
            }

        } catch (SQLException e) {
            LOG.error("Failed to getActiveSubscriptionsIds, userId={}", userId, e);
            throw new DaoException("Failed to getActiveSubscriptionsIds, userId=" + userId, e);
        }

        return subscriptionIds;
    }

    @Override
    public boolean removeSubscription(Long userId, Long periodicalId) {
        LOG.debug("Try to remove subscription id={} from user id={}", periodicalId, userId);

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(UNSUBSCRIBE_PERIODICAL_QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, periodicalId);

            result = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Failed to remove subscription id={} from user id={}", periodicalId, userId, e);
            throw new DaoException("Failed to move subscription userId=" + userId, e);
        }

        return result > 0;
    }

    @Override
    public boolean isSubscribedToPeriodical(long userId, long periodicalId) {
        LOG.debug("Try to check if user id={} is subscribed to periodical id={}", userId, periodicalId);

        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(IS_USER_SUBSCRIBED_TO_PERIODICAL_QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, periodicalId);

            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();

        } catch (SQLException e) {
            LOG.error("Failed to check if user id={} is subscribed to periodical id={}", userId, periodicalId, e);
            throw new DaoException("Failed to check if user id=" + userId + " is subscribed to periodical id=" + periodicalId, e);
        }

        return result;
    }

}
