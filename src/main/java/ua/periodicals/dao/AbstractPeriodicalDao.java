package ua.periodicals.dao;

import ua.periodicals.model.Periodical;

import java.util.List;

public abstract class AbstractPeriodicalDao extends AbstractDao<Periodical> {

    public abstract List<Periodical> getPerPage(int start, int total);

    public abstract Long getCount();

}
