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
import static ua.periodicals.util.AttributeNames.PERIODICAL_ID_ATTR;
import static ua.periodicals.util.Pages.CART_PAGE;

public class RemoveFromCart implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(RemoveFromCart.class);

    @Override
    public NextPage execute(HttpServletRequest request) {
        LOG.debug("Try to remove from cart, periodical id={}", request.getParameter(PERIODICAL_ID_ATTR));

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTR);
        long periodicalId = Long.parseLong(request.getParameter(PERIODICAL_ID_ATTR));

        cart.removeItem(periodicalId);
        session.setAttribute(CART_ATTR, cart);

        if (cart.getTotalCount() == 0) {
            session.removeAttribute(CART_ATTR);
        }

        return new NextPage(CART_PAGE, DispatchType.FORWARD);
    }
}
