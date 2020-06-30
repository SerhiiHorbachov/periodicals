package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static ua.periodicals.util.AttributeNames.CART_ATTR;
import static ua.periodicals.util.Pages.CART_PAGE;

public class CartView implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(CartView.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to display cart");

        HttpSession session = request.getSession();
        if (session.getAttribute(CART_ATTR) != null) {
            Cart cart = (Cart) session.getAttribute(CART_ATTR);
        }

        return new NextPage(CART_PAGE, DispatchType.FORWARD);
    }
}
