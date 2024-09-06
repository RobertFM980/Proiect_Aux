package ro.siit.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/profile", "/clients", "/banks"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            if (request.getSession().getAttribute("username") != null) {
                if( (request.getContextPath() + "/banks").equals(request.getRequestURI()) &&
                        request.getSession().getAttribute("isSuperuser") == null){
                        request.getRequestDispatcher("/jsps/notAuthorized/notAuthorized.jsp").forward(request, response);
                }
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
    }
}
