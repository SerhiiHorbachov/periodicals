package ua.periodicals.command.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.periodicals.model.User;
import ua.periodicals.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.periodicals.util.AttributeNames.PERIODICAL_ID_PARAM;
import static ua.periodicals.util.AttributeNames.USER_ATTR;
import static ua.periodicals.util.Pages.MY_SUBSCRIPTIONS_PAGE;

@ExtendWith(MockitoExtension.class)
class UnsubscribeTest {

    @Mock
    UserService userService;

    @Mock
    HttpSession session;

    @Mock
    HttpServletRequest request;

    @InjectMocks
    private static Unsubscribe unsubscribe;

    @BeforeAll
    static void setUp() {
        unsubscribe = new Unsubscribe();
    }

    @Disabled
    @Test
    void execute() {

        User userMock = new User();
        userMock.setId(1);

        when(userService.removePeriodicalFromActiveSubscriptions(anyLong(), anyLong())).thenReturn(true);
        when(session.getAttribute(USER_ATTR)).thenReturn(new User());

        assertTrue(unsubscribe.execute(request).getPage().equals(MY_SUBSCRIPTIONS_PAGE));
    }


}