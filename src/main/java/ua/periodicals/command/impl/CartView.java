package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartView implements ActionCommand {

    private static final String CART_ATTRIBUTE = "cart";
    private static final Logger LOG = LoggerFactory.getLogger(CartView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to display cart");

        HttpSession session = request.getSession();
        if (session.getAttribute(CART_ATTRIBUTE) != null) {
            Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        }

        return new NextPage("cart.jsp", "FORWARD");
    }
}
