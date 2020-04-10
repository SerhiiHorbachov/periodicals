package ua.periodicals.dao;

import ua.periodicals.exception.DaoException;
import ua.periodicals.model.Entity;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    protected Connection connection;

    public abstract List<T> findAll() throws DaoException;

    public abstract boolean create(T entity) throws DaoException;

    public abstract T findById(Long id) throws DaoException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
