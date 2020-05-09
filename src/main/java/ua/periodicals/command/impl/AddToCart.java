package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.impl.PeriodicalLogicImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddToCart implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {

        System.out.println(">>AddToCart action");

        NextPage next = new NextPage();
        HttpSession session = request.getSession();

        PeriodicalLogicImpl periodicalLogic = new PeriodicalLogicImpl();

        Periodical periodical = periodicalLogic.findById(Long.parseLong(request.getParameter("periodicalId")));

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {

            if (cart.getCartItems().contains(periodical)) {
                List<Periodical> periodicals = periodicalLogic.findAll();

                request.setAttribute("periodicals", periodicals);

                String message = "This item is already in the cart";
                Long invalidId = Long.parseLong(request.getParameter("periodicalId"));
                request.setAttribute("alreadyInCart", message);
                request.setAttribute("invalidId", invalidId);

                next.setPage("main.jsp");
                next.setDispatchType("FORWARD");
            } else {
                cart.addItem(periodical);
                session.setAttribute("cart", cart);
                next.setPage("/my/cart");
                next.setDispatchType("REDIRECT");
            }

        } else {
            cart = new Cart();

            cart.addItem(periodical);
            session.setAttribute("cart", cart);
            next.setPage("/my/cart");
            next.setDispatchType("REDIRECT");
        }

        System.out.println("[INFO] cart" + cart.toString());

        return next;
    }
}
