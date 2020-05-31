package ua.periodicals.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Periodical;
import ua.periodicals.model.User;
import ua.periodicals.service.impl.PeriodicalLogicImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddToCart implements ActionCommand {
    private final static String PERIODICAL_ID_REQUEST_PARAM = "periodicalId";
    private final static String CART_SESSION_ATTRIBUTE = "cart";

    private static final Logger LOG = LoggerFactory.getLogger(AddToCart.class);

    @Override
    public NextPage execute(HttpServletRequest request) {

        LOG.debug("Try to add to cart, periodical id={}, from page={}", request.getParameter(PERIODICAL_ID_REQUEST_PARAM), request.getParameter("currentPage"));

        NextPage next = new NextPage();
        HttpSession session = request.getSession();

        PeriodicalLogicImpl periodicalLogic = new PeriodicalLogicImpl();
        Long requestedPeriodicalId = Long.parseLong(request.getParameter(PERIODICAL_ID_REQUEST_PARAM));
        User user = (User) session.getAttribute("user");


        Periodical periodical = periodicalLogic.findById(requestedPeriodicalId);

        Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);

        if (periodicalLogic.isSubscribedToPeriodical(user.getId(), requestedPeriodicalId)
            || itemIsInCart(cart, periodical)) {

            List<Periodical> periodicals = periodicalLogic.findAll();

            request.setAttribute("periodicals", periodicals);

            Long invalidId = Long.parseLong(request.getParameter("periodicalId"));
            request.setAttribute("invalidId", invalidId);

            next.setPage("/?page=" + request.getParameter("currentPage") + "&inv_id=" + invalidId);
            next.setDispatchType("REDIRECT");

        } else {
            if (cart == null) {
                cart = new Cart();
            }

            cart.addItem(periodical);
            session.setAttribute("cart", cart);
            next.setPage("/my/cart");
            next.setDispatchType("REDIRECT");
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

