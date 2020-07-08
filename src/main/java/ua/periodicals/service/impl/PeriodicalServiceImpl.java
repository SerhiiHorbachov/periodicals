package ua.periodicals.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.dao.EntityTransaction;
import ua.periodicals.dao.impl.PeriodicalDao;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.ConnectionManager;
import ua.periodicals.exception.DaoException;
import ua.periodicals.exception.LogicException;
import ua.periodicals.exception.ValidationException;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.PeriodicalService;

import java.util.List;

/**
 * Package access. New instance should be created in @see ServiceManager
 * Database Connection manager should be injected via constructor.
 *
 * @author Serhii Hor
 */
class PeriodicalServiceImpl implements PeriodicalService {
    private static final Logger LOG = LoggerFactory.getLogger(PeriodicalServiceImpl.class);

    private ConnectionManager connectionManager;
    private PeriodicalDao periodicalDao;
    private UserDao userDao;

    /**
     * Constructor.
     * DAOs are instantiated in the constructor.
     *
     * @param connectionManager provider for database connection.
     */
    public PeriodicalServiceImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        periodicalDao = new PeriodicalDao();
        userDao = new UserDao();
    }

    /**
     * Find all periodicals from database
     *
     * @return List<Periodical>
     * @throws LogicException
     */
    public List<Periodical> findAll() {
        LOG.debug("Try to find all periodicals");

        List<Periodical> periodicals;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {

            transaction.begin(periodicalDao);
            periodicals = periodicalDao.findAll();
            transaction.commit();

        } catch (DaoException e) {
            LOG.error("Failed to get all periodicals");
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {

            try {
                transaction.end();
            } catch (DaoException e) {
                LOG.error("Failed to complete transaction");
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return periodicals;
    }

    /**
     * Count all stored periodicals
     *
     * @return Long total number of stored items.
     * @throws LogicException
     */
    public Long getCount() {
        Long count;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(periodicalDao);
            count = periodicalDao.getCount();
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return count;
    }

    /**
     * Finds periodicals per requested items.
     *
     * @param page  requested page
     * @param total total number of items per page
     * @return List<Periodical>
     * @throws LogicException
     */
    public List<Periodical> getPerPage(int page, int total) {

        List<Periodical> periodicals;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        int start = page;

        if (page != 1) {
            start = (page - 1) * total + 1;
        }

        try {
            transaction.begin(periodicalDao);
            periodicals = periodicalDao.getPerPage(start, total);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return periodicals;
    }

    /**
     * Finds periodical by its id.
     *
     * @param id periodical Id.
     * @return Periodical
     * @throws LogicException
     */
    public Periodical findById(long id) {

        Periodical periodical;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(periodicalDao);
            periodical = periodicalDao.findById(id);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return periodical;
    }

    /**
     * Stores periodical in the database. In case validation fails, exception is thrown.
     *
     * @param periodical
     * @return boolean
     * @throws ValidationException is thrown in case validation fails
     * @throws LogicException
     */
    public boolean create(Periodical periodical) {

        boolean result = false;

        validatePeriodical(periodical);

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(periodicalDao);
            result = periodicalDao.create(periodical);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return result;
    }

    /**
     * Updates periodical stored in the database
     *
     * @param periodical Periodical to be updated.
     * @return boolean
     * @throws ValidationException is thrown in case validation fails
     * @throws LogicException
     */
    public boolean update(Periodical periodical) {
        boolean result = false;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(periodicalDao);
            result = periodicalDao.update(periodical);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return result;
    }

    /**
     * Deletes periodical by its id.
     *
     * @param id periodical id.
     * @return boolean
     * @throws LogicException
     */
    public boolean delete(long id) {
        boolean result = false;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(periodicalDao);
            periodicalDao.deleteById(id);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return result;
    }

    /**
     * Checks if User is subscribed to the Periodical
     *
     * @param userId       user id
     * @param periodicalId periodical id
     * @return boolean
     * @throws LogicException
     */
    public boolean isSubscribedToPeriodical(long userId, long periodicalId) {
        LOG.debug("Try to check if user id={} is subscribed to periodical id={}", userId, periodicalId);

        boolean result = false;

        EntityTransaction transaction = new EntityTransaction(connectionManager.getConnection());

        try {
            transaction.begin(userDao);

            result = userDao.isSubscribedToPeriodical(userId, periodicalId);

            transaction.commit();
        } catch (DaoException e) {
            LOG.error("Failed to end transaction.", e);
            transaction.rollback();
            throw new LogicException("Failed to perform transaction", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOG.error("Failed to end transaction.", e);
                transaction.rollback();
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return result;

    }

    /**
     * Validates a Periodical by its fields.
     *
     * @param periodical Periodical to be validated
     * @throws ValidationException
     */
    private void validatePeriodical(Periodical periodical) {
        if (periodical.getName() == null || periodical.getName().isEmpty() || periodical.getName().isBlank()) {
            throw new ValidationException("Periodical name cannot be empty.");
        }

        if (periodical.getMonthlyPrice() < 0) {
            throw new ValidationException("Price mist be a positive digit.");
        }

    }
}
