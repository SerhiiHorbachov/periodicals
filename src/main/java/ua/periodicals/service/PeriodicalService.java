package ua.periodicals.service;

import ua.periodicals.model.Periodical;

import java.util.List;

public interface PeriodicalService {

    List<Periodical> findAll();

    Long getCount();

    List<Periodical> getPerPage(int page, int total);

    Periodical findById(long id);

    boolean create(Periodical periodical);

    boolean update(Periodical periodical);

    boolean delete(long id);

    boolean isSubscribedToPeriodical(long userId, long periodicalId);
}
