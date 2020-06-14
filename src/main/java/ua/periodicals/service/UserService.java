package ua.periodicals.service;

import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;

import java.util.List;

public interface UserService {
    User authenticate(String email, String password);

    User findById(Long id);

    boolean create(User user);

    List<Periodical> getActiveSubscriptions(long userId);

    boolean removePeriodicalFromActiveSubscriptions(long userId, long periodicalId);
}
