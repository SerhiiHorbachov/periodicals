package ua.periodicals.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.impl.AddToCart;
import ua.periodicals.dao.AbstractUserDao;
import ua.periodicals.dao.EntityTransaction;
import ua.periodicals.dao.impl.PeriodicalDao;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.exception.*;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserLogicImpl {
    private final static String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{6,20})";

    private static final Logger LOG = LoggerFactory.getLogger(UserLogicImpl.class);

    public User authenticate(String email, String password) throws AuthenticationException, InvalidPasswordException {

        User user;

        AbstractUserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();

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

    public User findById(Long id) {

        User user;

        AbstractUserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();

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

    public User findByEmail(String email) {

        User user = null;

        AbstractUserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();

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
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return user;
    }

    public boolean create(User user) {

        boolean isCreated = false;

        validateUser(user);

        AbstractUserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();

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

    public List<Periodical> getActiveSubscriptions(long userId) {

        LOG.debug("Try to get active subscriptions for user id={}", userId);

        List<Periodical> subscriptions = new ArrayList<>();

        AbstractUserDao userDao = new UserDao();
        PeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

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

    public boolean removePeriodicalFromActiveSubscriptions(long userId, long periodicalId) {
        LOG.debug("Try to remove subscription id={} from user id={}", periodicalId, userId);

        int result = 0;

        AbstractUserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();

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
