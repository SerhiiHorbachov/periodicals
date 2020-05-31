package ua.periodicals.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(PeriodicalDao.class);

    @Override
    public List<Periodical> findAll() throws DaoException {
        LOG.debug("Try to findAll periodicals");

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
            LOG.error("Failed to get all periodicals.", e);
            throw new DaoException("Failed to get all periodicals.", e);
        }

        return periodicals;
    }

    @Override
    public List<Periodical> getPerPage(int start, int total) throws DaoException {
        LOG.debug("Try to get range of periodicals: start={}, total={}", start, total);

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
            LOG.error("Failed to get range of periodicals: start={}, total={}", start, total);
            throw new DaoException(String.format("Failed to get periodicals in range: start=%d, total=%d", start, total), e);
        }

        return periodicals;
    }

    @Override
    public Long getCount() {
        LOG.debug("Try to get count of periodicals.");

        Long count = null;

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(COUNT_ALL_QUERY);

            while (resultSet.next()) {

                count = resultSet.getLong(1);
                System.out.println("count: " + count);

            }

        } catch (SQLException e) {
            throw new DaoException("Failed to count periodicals", e);
        }

        return count;
    }

    @Override
    public Periodical findById(Long id) throws DaoException {
        LOG.debug("Try to find periodical by id={}", id);

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
            LOG.error("Failed to get periodical by id: {}", id, e);
            throw new DaoException("Failed to get periodical", e);
        }

        return periodical;
    }

    @Override
    public boolean create(Periodical periodical) throws DaoException {
        LOG.debug("Try to create periodical: {}", periodical);

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(CREATE_PERIODICAL_QUERY)) {
            statement.setString(1, periodical.getName());
            statement.setString(2, periodical.getDescription());
            statement.setLong(3, periodical.getMonthlyPrice());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Failed to create new periodical: {}", periodical);
            throw new DaoException("Failed to create new periodical", e);
        }

        return result > 0;
    }

    @Override
    public boolean update(Periodical periodical) throws DaoException {
        LOG.debug("Try to update periodical: {}", periodical);

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PERIODICAL_QUERY)) {
            statement.setString(1, periodical.getName());
            statement.setString(2, periodical.getDescription());
            statement.setLong(3, periodical.getMonthlyPrice());
            statement.setLong(4, periodical.getId());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Failed to update periodical: {}", periodical);
            throw new DaoException("Failed to update periodical", e);
        }

        return result > 0;
    }

    @Override
    public boolean deleteById(Long periodicalId) throws DaoException {
        LOG.debug("Try to delete periodical by id={}", periodicalId);

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(DELETE_PERIODICAL_QUERY)) {
            statement.setLong(1, periodicalId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Failed to delete periodical id={}", periodicalId);
            throw new DaoException(e);
        }

        return result > 0;
    }


}
