package controller.util;

import controller.Payment;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            if (ses != null && ses.getAttribute("telephone") != null) {
                if (!reqURI.contains("/signUp.xhtml")
                        && !reqURI.contains("/index.xhtml")
                        && !reqURI.contains("/password_recovery.xhtml")) {
                    chain.doFilter(request, response);
                } else {
                    resp.sendRedirect(reqt.getContextPath() + "/faces/home.xhtml");
                }
            } else if (!reqURI.contains("/index.xhtml")
                    && !reqURI.contains("/public/")
                    && !reqURI.contains("/signUp.xhtml")
                    && !reqURI.contains("/rules.xhtml")
                    && !reqURI.contains("javax.faces.resource")
                    && !reqURI.contains("/password_recovery.xhtml")
                    && !reqURI.contains("/payment.xhtml")) {
                resp.sendRedirect(reqt.getContextPath() + "/faces/index.xhtml");
            } else if (reqURI.contains("/payment.xhtml") && Payment.getAmount() == 0.0) {
                resp.sendRedirect(reqt.getContextPath() + "/faces/index.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
