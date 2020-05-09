package ua.periodicals.command.impl;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveFromCart implements ActionCommand {
    @Override
    public NextPage execute(HttpServletRequest request) {

        System.out.println(">>RemoveFromCart");

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        long periodicalId = Long.parseLong(request.getParameter("periodicalId"));

        cart.removeItem(periodicalId);
        session.setAttribute("cart", cart);

        if (cart.getTotalCount() == 0) {
            session.removeAttribute("cart");
        }

        return new NextPage("cart.jsp", "FORWARD");
    }
}
