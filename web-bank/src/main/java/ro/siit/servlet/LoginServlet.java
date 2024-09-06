package ro.siit.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/login/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username.equals("admin") && password.equals("admin")) {
            req.getSession().setAttribute("username", username);
            resp.sendRedirect(getServletContext().getContextPath() + "/profile");
        } else if(username.equals("superuser") && password.equals("superuser")) {
            req.getSession().setAttribute("username", username);
            req.getSession().setAttribute("isSuperuser", "true");
            resp.sendRedirect(getServletContext().getContextPath() + "/profile");
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/jsps/login/login.jsp").forward(req, resp);
        }
    }
}
