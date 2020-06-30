package ua.periodicals.command.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegisterViewTest {

    private static RegisterView registerView;

    @BeforeAll
    static void setUp() {
        registerView = new RegisterView();
    }

    @Test
    void execute() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        assertTrue(registerView.execute(request).getPage().equals("register.jsp"));
    }

}