package ua.periodicals.filter;

import ua.periodicals.logic.UserLogic;
import ua.periodicals.model.User;
import ua.periodicals.resource.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

//@WebFilter("/controller")
public class AuthFilter implements Filter {
    public void destroy() {
        System.out.println("#INFO Session filter destroy()");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
        /**/
        System.out.println("#INFO AuthFilter doFilter() started");


        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        //Logged user
        if (nonNull(session.getAttribute("user"))) {

            String role = (String) session.getAttribute("role");

            User user = (User) session.getAttribute("user");
            System.out.println("#INFO AuthFilter session in not null. Login: " + user.getEmail());

//            chain.doFilter(request, response);
//            moveToMenu(req, res, role);

        } else {
//            moveToMenu(req, res, User.ROLE.GUEST.toString());
            System.out.println("#INFO AuthFilter session failed, forwarding to login.. ");

//            req.getRequestDispatcher("path.page.login").forward(req, res);
            res.sendRedirect(ConfigurationManager.getProperty("path.page.login"));
        }


//        chain.doFilter(req, res);
    }

    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final String role)
            throws ServletException, IOException {


        if (role.toUpperCase().equals(User.ROLE.ADMIN)) {

            req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.admin")).forward(req, res);

        } else if (role.toUpperCase().equals(User.ROLE.USER)) {

            req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.main")).forward(req, res);

        } else {
            req.getRequestDispatcher("path.page.login").forward(req, res);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("#INFO Session filter Init");
    }

}
