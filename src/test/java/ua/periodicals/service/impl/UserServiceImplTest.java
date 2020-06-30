package ua.periodicals.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.runners.MockitoJUnitRunner;
import ua.periodicals.dao.EntityTransaction;
import ua.periodicals.dao.impl.UserDao;
import ua.periodicals.database.ConnectionManager;
import ua.periodicals.database.ConnectionManagerImpl;
import ua.periodicals.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private static UserServiceImpl userService;
    private static ConnectionManager connectionManager;

    @BeforeAll
    static void setUp() {
        connectionManager = new ConnectionManagerImpl();
        userService = new UserServiceImpl(connectionManager);
    }

    @Test
    void smokeTest() {
        assertTrue(true);
    }

    @Test
    void findById_ShouldReturnUserWithMatchingId() {
        long id = 1;
        User mockedUser = new User();
        mockedUser.setId(id);


        EntityTransaction entityTransaction = mock(EntityTransaction.class);
        UserDao userDao = mock(UserDao.class);

        when(userDao.findById(id)).thenReturn(mockedUser);
        assertTrue(true);

        assertNotNull(userService.findById(id));
    }

}