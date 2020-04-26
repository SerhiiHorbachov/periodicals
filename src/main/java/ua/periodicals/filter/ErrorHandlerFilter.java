package ua.periodicals.filter;

import ua.periodicals.util.RoutingUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ErrorHandlerFilter")
public class ErrorHandlerFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        try {
            chain.doFilter(req, resp);
        } catch (Throwable th) {
            String requestedUrl = request.getRequestURI();
            //Logger.error("Request " + requestedUrl + " failed: " + th.getMessage(), th);
            RoutingUtils.forwardToPage("error.jsp", request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
