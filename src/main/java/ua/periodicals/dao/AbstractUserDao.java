package ua.periodicals.dao;

import ua.periodicals.exception.DaoException;
import ua.periodicals.model.User;

public abstract class AbstractUserDao extends AbstractDao<User> {

    public abstract User findByEmailAndPassword(String email, String pwdHash) throws DaoException;

    public abstract User findByEmail(String email);

}
