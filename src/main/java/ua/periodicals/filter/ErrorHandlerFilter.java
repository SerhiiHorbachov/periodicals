package ua.periodicals.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.periodicals.command.impl.admin.EditPeriodicalView;
import ua.periodicals.util.RoutingUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class ErrorHandlerFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandlerFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        try {
            chain.doFilter(req, resp);
        } catch (Throwable th) {

            System.out.println("Error cought!!!" + th);
            String requestedUrl = request.getRequestURI();

            LOG.error("Request " + requestedUrl + " failed: " + th.getMessage(), th);
            request.setAttribute("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", th.getMessage());
            RoutingUtils.forwardToPage("error/error.jsp", request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
