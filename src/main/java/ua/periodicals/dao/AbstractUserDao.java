package ua.periodicals.dao;

import ua.periodicals.exception.DaoException;
import ua.periodicals.model.User;

import java.util.List;

public abstract class AbstractUserDao extends AbstractDao<User> {

    public abstract User findByEmailAndPassword(String email, String pwdHash) throws DaoException;

    public abstract User findByEmail(String email);

    public abstract boolean addSubscription(long userId, long periodicalId);

    public abstract List<Long> getActiveSubscriptionsIds(long userId);

    public abstract boolean removeSubscription(Long userId, Long periodicalId);

    public abstract boolean isSubscribedToPeriodical(long userId, long periodicalId);

}
