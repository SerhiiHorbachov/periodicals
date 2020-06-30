package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.impl.ServiceManager;
import ua.periodicals.util.DispatchType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static ua.periodicals.util.AttributeNames.*;
import static ua.periodicals.util.Pages.MY_CART_PATH;

public class AddToCart implements ActionCommand {
    private static final Logger LOG = LoggerFactory.getLogger(AddToCart.class);

    @Override
    public NextPage execute(HttpServletRequest request) {

        LOG.debug("Try to add to cart, periodical id={}, from page={}", request.getParameter(PERIODICAL_ID_REQUEST_PARAM), request.getParameter("currentPage"));

        NextPage next = new NextPage();
        HttpSession session = request.getSession();

        PeriodicalService periodicalLogic = ServiceManager.getInstance().getPeriodicalService();
        Long requestedPeriodicalId = Long.parseLong(request.getParameter(PERIODICAL_ID_REQUEST_PARAM));
        User user = (User) session.getAttribute(USER_ATTR);

        Periodical periodical = periodicalLogic.findById(requestedPeriodicalId);

        Cart cart = (Cart) session.getAttribute(CART_ATTR);

        if (periodicalLogic.isSubscribedToPeriodical(user.getId(), requestedPeriodicalId)
            || itemIsInCart(cart, periodical)) {

            List<Periodical> periodicals = periodicalLogic.findAll();

            request.setAttribute(PERIODICALS_ATTR, periodicals);

            Long invalidId = Long.parseLong(request.getParameter(PERIODICAL_ID_ATTR));
            request.setAttribute(INVALID_ID_ATTR, invalidId);

            next.setPage("/?page=" + request.getParameter(CURRENT_PAGE_ATTR) + "&inv_id=" + invalidId);
            next.setDispatchType(DispatchType.REDIRECT);

        } else {
            if (cart == null) {
                cart = new Cart();
            }

            cart.addItem(periodical);
            session.setAttribute(CART_ATTR, cart);
            next.setPage(MY_CART_PATH);
            next.setDispatchType(DispatchType.REDIRECT);
        }

        return next;
    }

    private boolean itemIsInCart(Cart cart, Periodical periodical) {
        boolean isInCart = false;
        if (cart != null) {
            if (cart.getCartItems().contains(periodical)) {
                isInCart = true;
            }
        }

        return isInCart;
    }
}
