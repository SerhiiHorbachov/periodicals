package ua.periodicals.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.dao.EntityTransaction;
import ua.periodicals.dao.impl.PeriodicalDao;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.ConnectionManager;
import ua.periodicals.exception.*;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;
import ua.periodicals.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Package access. New instance should be created in @see ServiceManager
 * Database Connection manager should be injected via constructor.
 *
 * @author Serhii Hor
 */
class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private ConnectionManager connectionManager;

    private UserDao userDao;
    private PeriodicalDao periodicalDao;

    /**
     * Constructor.
     *
     * @param connectionManager provider for database connection.
     */
    public UserServiceImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.userDao = new UserDao();
        this.periodicalDao = new PeriodicalDao();
    }

    /**
     * Authenticates user by his email and password.
     * If authentication doesn't pass, AuthenticationException is thrown
     *
     * @param email    user email
     * @param password user password
     * @return User
     * @throws AuthenticationException
     * @throws InvalidPasswordException
     * @throws LogicException
     */
    public User authenticate(String email, String password) throws AuthenticationException, InvalidPasswordException {

        User user;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(userDao);
            user = userDao.findByEmail(email);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                throw new LogicException("Failed to authenticate user with email: " + email, e);
            }
        }

        if (user != null) {
            boolean isPasswordValid = BCrypt.checkpw(password, user.getPasswordHash());
            if (isPasswordValid) {
                return user;
            } else {
                throw new InvalidPasswordException("Invalid password");
            }
        } else {
            throw new AuthenticationException("User with email" + email + "is not registered.");
        }

    }

    /**
     * Finds user stored in the database by Id.
     *
     * @param id user id
     * @return User
     * @throws LogicException
     */
    public User findById(long id) {

        User user;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(userDao);

            user = userDao.findById(id);

            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return user;
    }

    /**
     * Stores new user in the database. In case user object doesn't pass validation, exception is thrown.
     *
     * @param user new User to be stored in database
     * @return boolean
     * @throws LogicException
     * @throws ValidationException
     */
    public boolean create(User user) {

        boolean isCreated = false;

        validateUser(user);

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(userDao);

            if (userDao.findByEmail(user.getEmail()) != null) {
                throw new DuplicateException("Email " + user.getEmail() + "already exist");
            }

            isCreated = userDao.create(user);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to create new user", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return isCreated;

    }

    /**
     * Find user's active subscriptions
     *
     * @param userId user Id
     * @return List<Periodical>
     * @throws LogicException
     */
    public List<Periodical> getActiveSubscriptions(long userId) {

        LOG.debug("Try to get active subscriptions for user id={}", userId);

        List<Periodical> subscriptions = new ArrayList<>();

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(userDao, periodicalDao);

            List<Long> subscriptionsIds = userDao.getActiveSubscriptionsIds(userId);

            for (Long subscriptionId : subscriptionsIds) {
                subscriptions.add(periodicalDao.findById(subscriptionId));
            }

            transaction.commit();
        } catch (DaoException e) {
            LOG.error("Failed to get active subscriptions for user id={}", userId, e);
            transaction.rollback();
            throw new LogicException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOG.error("Failed to end transaction.", e);
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return subscriptions;
    }

    /**
     * Removes periodical from user's active subscription.
     *
     * @param userId       user id.
     * @param periodicalId periodicals id.
     * @return boolean
     */
    public boolean removePeriodicalFromActiveSubscriptions(long userId, long periodicalId) {
        LOG.debug("Try to remove subscription id={} from user id={}", periodicalId, userId);

        int result = 0;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(userDao);

            userDao.removeSubscription(userId, periodicalId);

            transaction.commit();
        } catch (DaoException e) {
            LOG.error("Failed to remove subscription id={} from user id={}", periodicalId, userId, e);
            transaction.rollback();
            throw new LogicException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOG.error("Failed to end transaction.", e);
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return result > 0;
    }

    /**
     * Validates a user.
     *
     * @param user user to be validated
     * @throws ValidationException
     */
    private void validateUser(User user) {
        if (user.getFirstName().isEmpty() || user.getFirstName().isBlank()) {
            throw new ValidationException("First name cannot be blank or empty");
        }

        if (user.getLastName().isEmpty() || user.getLastName().isBlank()) {
            throw new ValidationException("Last name cannot be blank or empty");
        }

        if (user.getUserRole() != User.Role.USER) {
            throw new ValidationException("User role is not set");
        }

        if (user.getEmail().isEmpty() || user.getEmail().isBlank()) {
            throw new ValidationException("Email is not set");
        }

        if (user.getPasswordHash().isBlank() || user.getPasswordHash().isEmpty()) {
            throw new ValidationException("Password is not set");
        }

    }

}
