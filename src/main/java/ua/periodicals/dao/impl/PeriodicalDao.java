package ua.periodicals.dao.impl;


import ua.periodicals.dao.AbstractPeriodicalDao;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalDao extends AbstractPeriodicalDao {

    private static final String FIND_ALL_PERIODICALS_QUERY = "SELECT * FROM periodicals";

    @Override
    public List<Periodical> findAll() throws DaoException {
        List<Periodical> periodicals = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(FIND_ALL_PERIODICALS_QUERY);

            while (resultSet.next()) {

                int periodicalId = resultSet.getInt("periodical_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int monthlyPrice = resultSet.getInt("monthly_price_cents");

                Periodical tempPeriodical = new Periodical(periodicalId, name, description, monthlyPrice);
                periodicals.add(tempPeriodical);

            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return periodicals;
    }

    @Override
    public Periodical findById(Long id) throws DaoException {
        return null;
    }

    @Override
    public boolean create(Periodical entity) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Periodical entity) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }


}
