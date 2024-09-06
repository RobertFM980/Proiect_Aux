package ro.siit.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ro.siit.entity.Bank;
import ro.siit.service.BankService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/banks"})
public class BanksServlet extends HttpServlet {
    private Connection dbConnection;
    private BankService bankService;

    @Override
    public void init(ServletConfig config) {
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/MyDB");
            dbConnection = ds.getConnection();
            bankService = new BankService(dbConnection);
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = readActionParam(req);
        switch (action) {
            case "add":
                req.setAttribute("addBanksActive", "active");
                req.getRequestDispatcher("/jsps/bank/add.jsp").forward(req, resp);
                break;
                case "edit":
                    String bankId = req.getParameter("bankId");
                    Bank bank  = bankService.findById(UUID.fromString(bankId));
                    req.setAttribute("bank", bank);
                    req.setAttribute("addBanksActive", "active");
                    req.getRequestDispatcher("/jsps/bank/edit.jsp").forward(req, resp);
                    break;
                case "delete":
                    bankId = req.getParameter("bankId");
                    bankService.delete(UUID.fromString(bankId));
                    listBanks(req, resp);
                    break;
            default:
                listBanks(req, resp);
        }
    }

    private static String readActionParam(HttpServletRequest req) {
        String action = req.getParameter("action");
        action = (action == null) ? "list" : action;
        return action;
    }

    private void listBanks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Bank> banks = bankService.findAll();
        req.setAttribute("banks", banks);
        req.setAttribute("listBanksActive", "active");
        req.getRequestDispatcher("/jsps/bank/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = readActionParam(req);
        switch (action) {
            case "add":
                String name = req.getParameter("name");
                String city = req.getParameter("city");
                bankService.create(new Bank(name));
                listBanks(req, resp);
                break;
                case "edit":
                    String bankId = req.getParameter("bankId");
                    String bankName = req.getParameter("name");
                    bankService.update(UUID.fromString(bankId), bankName);
                    listBanks(req, resp);
        }
    }
}
