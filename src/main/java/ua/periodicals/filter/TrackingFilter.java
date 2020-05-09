package ua.periodicals.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "TrackingFilter")
public class TrackingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();

        System.out.println("[INFO] AuthFilter getContextPath(): " + request.getContextPath());
        System.out.println("[INFO] AuthFilter getRequestURL(): " + request.getRequestURL());
        System.out.println("[INFO] AuthFilter getRequestURI(): " + request.getRequestURI());
        System.out.println("[INFO] AuthFilter getServletPath(): " + request.getServletPath());
        
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
