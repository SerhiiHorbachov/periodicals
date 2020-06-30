package ua.periodicals.command.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartViewTest {

    private static CartView cartView;

    @BeforeAll
    static void setUp() {
        cartView = new CartView();
    }
    
    @Test
    void execute() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);

        assertNotNull(cartView.execute(request));
        assertTrue(cartView.execute(request).getPage().equals("cart.jsp"));
    }

}