package ua.periodicals.dao.impl;

import ua.periodicals.dao.AbstractPeriodicalDao;
import ua.periodicals.exception.DaoException;
import ua.periodicals.model.Periodical;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalDao extends AbstractPeriodicalDao {

    private static final String FIND_ALL_PERIODICALS_QUERY = "SELECT * FROM periodicals";
    private static final String FIND_PERIODICAL_BY_ID_QUERY = "SELECT * FROM periodicals WHERE periodical_id=?";
    private static final String CREATE_PERIODICAL_QUERY = "INSERT INTO periodicals(name, description, monthly_price_cents) VALUES(?, ?, ?)";
    private static final String UPDATE_PERIODICAL_QUERY = "UPDATE periodicals SET name=?, description=?, monthly_price_cents=? WHERE periodical_id=?";
    private static final String DELETE_PERIODICAL_QUERY = "DELETE FROM periodicals WHERE periodical_id=?";
    private static final String FIND_PERIODICALS_PER_PAGE_QUERY = " SELECT * FROM periodicals ORDER BY periodical_id LIMIT ? OFFSET (?)";
    private static final String COUNT_ALL_QUERY = "SELECT COUNT(*) FROM periodicals";

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
    public List<Periodical> getPerPage(int start, int total) throws DaoException {
        List<Periodical> periodicals = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_PERIODICALS_PER_PAGE_QUERY)) {
            statement.setInt(1, total);
            statement.setInt(2, start - 1);
            ResultSet resultSet = statement.executeQuery();

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
    public Long getCount() {
        Long count = null;

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(COUNT_ALL_QUERY);

            while (resultSet.next()) {

                count = resultSet.getLong(1);
                System.out.println("count: " + count);

            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return count;
    }

    @Override
    public Periodical findById(Long id) throws DaoException {
        Periodical periodical = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_PERIODICAL_BY_ID_QUERY)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int periodicalId = resultSet.getInt("periodical_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int priceCents = resultSet.getInt("monthly_price_cents");

                periodical = new Periodical(periodicalId, name, description, priceCents);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return periodical;
    }

    @Override
    public boolean create(Periodical periodical) throws DaoException {
        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(CREATE_PERIODICAL_QUERY)) {
            statement.setString(1, periodical.getName());
            statement.setString(2, periodical.getDescription());
            statement.setInt(3, periodical.getMonthlyPrice());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result > 0;
    }

    @Override
    public boolean update(Periodical periodical) throws DaoException {
        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PERIODICAL_QUERY)) {
            statement.setString(1, periodical.getName());
            statement.setString(2, periodical.getDescription());
            statement.setInt(3, periodical.getMonthlyPrice());
            statement.setLong(4, periodical.getId());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result > 0;
    }

    @Override
    public boolean deleteById(Long periodicalId) throws DaoException {
        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(DELETE_PERIODICAL_QUERY)) {
            statement.setLong(1, periodicalId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result > 0;
    }


}
