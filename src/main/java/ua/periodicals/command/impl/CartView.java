package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartView implements ActionCommand {

    @Override
    public NextPage execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("cart") != null) {
            Cart cart = (Cart) session.getAttribute("cart");

        }

        return new NextPage("cart.jsp", "FORWARD");
    }
}
