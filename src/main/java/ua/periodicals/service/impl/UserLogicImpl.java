package ua.periodicals.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import ua.periodicals.dao.AbstractUserDao;
import ua.periodicals.dao.EntityTransaction;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.exception.*;
import ua.periodicals.model.User;

public class UserLogicImpl {
    private final static String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{6,20})";

    public User authenticate(String email, String password) throws AuthenticationException, InvalidPasswordException {

        User user;

        AbstractUserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();

        try {
            transaction.begin(userDao);
            user = userDao.findByEmail(email);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                throw new LogicException("Failed to authenticate user with email: " + email, e);
            }
        }

        if (user != null) {
            boolean isPasswordValid = BCrypt.checkpw(password, user.getPasswordHash());
            if (isPasswordValid) {
                return user;
            } else {
                throw new InvalidPasswordException("Invalid password");
            }
        } else {
            throw new AuthenticationException("User with email" + email + "is not registered.");
        }

    }

    public User findByEmail(String email) {

        User user = null;

        AbstractUserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();

        try {
            transaction.begin(userDao);
            user = userDao.findByEmail(email);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException(e);
        } finally {

            try {
                transaction.end();
            } catch (DaoException e) {
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return user;
    }

    public boolean create(User user) {

        boolean isCreated = false;

        validateUser(user);

        AbstractUserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();

        try {
            transaction.begin(userDao);

            if (userDao.findByEmail(user.getEmail()) != null) {
                throw new DuplicateException("Email " + user.getEmail() + "already exist");
            }

            isCreated = userDao.create(user);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new LogicException("Failed to create new user", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                throw new LogicException("Failed to end transaction", e);
            }
        }

        return isCreated;

    }

    private void validateUser(User user) {
        if (user.getFirstName().isEmpty() || user.getFirstName().isBlank()) {
            throw new ValidationException("First name cannot be blank or empty");
        }

        if (user.getLastName().isEmpty() || user.getLastName().isBlank()) {
            throw new ValidationException("Last name cannot be blank or empty");
        }

        if (user.getUserRole() != User.Role.USER) {
            throw new ValidationException("User role is not set");
        }

        if (user.getEmail().isEmpty() || user.getEmail().isBlank()) {
            throw new ValidationException("Email is not set");
        }

        if (user.getPasswordHash().isBlank() || user.getPasswordHash().isEmpty()) {
            throw new ValidationException("Password is not set");
        }

    }


}
