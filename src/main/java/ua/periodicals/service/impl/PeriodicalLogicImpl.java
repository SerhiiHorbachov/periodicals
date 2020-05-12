package ua.periodicals.service.impl;

import ua.periodicals.dao.AbstractPeriodicalDao;
import ua.periodicals.dao.EntityTransaction;
import ua.periodicals.dao.impl.PeriodicalDao;
import ua.periodicals.exception.DaoException;
import ua.periodicals.exception.LogicException;
import ua.periodicals.model.Periodical;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalLogicImpl {

    public List<Periodical> findAll() {

        List<Periodical> periodicals = new ArrayList<>();

        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

        try {
            transaction.begin(periodicalDao);
            periodicals = periodicalDao.findAll();
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

    public Long getCount() {
        Long count = null;

        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

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

    public List<Periodical> getPerPage(int page, int total) {

        List<Periodical> periodicals = new ArrayList<>();

        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

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


    public Periodical findById(long id) {

        Periodical periodical = null;

        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

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

    public boolean create(Periodical periodical) {

        boolean result = false;

        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

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

    public boolean update(Periodical periodical) {
        boolean result = false;

        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

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

    public boolean delete(long id) {
        boolean result = false;

        AbstractPeriodicalDao periodicalDao = new PeriodicalDao();
        EntityTransaction transaction = new EntityTransaction();

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
}
