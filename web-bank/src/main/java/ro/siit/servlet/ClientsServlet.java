package ro.siit.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import ro.siit.entity.Client;
import ro.siit.service.ClientService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/clients", "/otherClients"})
public class ClientsServlet extends HttpServlet {
    private Connection dbConnection;
    private ClientService clientService;

    @Override
    public void init(ServletConfig config) {
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/MyDB");
            dbConnection = ds.getConnection();
            clientService = new ClientService(dbConnection);
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = readActionParam(req);
        switch (action) {
            case "add":
                req.setAttribute("addClientsActive", "active");
                req.getRequestDispatcher("/jsps/client/add.jsp").forward(req, resp);
                break;
                case "edit":
                    String clientId = req.getParameter("clientId");
                    Client client  = clientService.findById(UUID.fromString(clientId));
                    req.setAttribute("client", client);
                    req.setAttribute("addClientsActive", "active");
                    req.getRequestDispatcher("/jsps/client/edit.jsp").forward(req, resp);
                    break;
                case "delete":
                    clientId = req.getParameter("clientId");
                    clientService.delete(UUID.fromString(clientId));
                    listClients(req, resp);
                    break;
                    case "download":
                        download(resp);
                        break;
            default:
                listClients(req, resp);
        }
    }

    private void download(HttpServletResponse resp) throws IOException {

        List<Client> clients = clientService.findAll();

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"download.pdf\"");

        PDDocument document = new PDDocument();

        PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        String[][] content = new String[clients.size()][];
        for (int i = 0; i < clients.size(); i++) {
            String[] clientLine = new String[]{
                    clients.get(i).getId().toString(),
                    clients.get(i).getName(),
                    clients.get(i).getCity(),
                    clients.get(i).getFormattedBirthDate()
            };
            content[i] = clientLine;
        }

        drawTable(contentStream, 700, 100, content);
        contentStream.close();

        document.save(resp.getOutputStream());
    }

    private static String readActionParam(HttpServletRequest req) {
        String action = req.getParameter("action");
        action = (action == null) ? "list" : action;
        return action;
    }

    private void listClients(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Client> clients = clientService.findAll();
        req.setAttribute("clients", clients);
        req.setAttribute("listClientsActive", "active");
        req.getRequestDispatcher("/jsps/client/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = readActionParam(req);
        switch (action) {
            case "add":
                String name = req.getParameter("name");
                String city = req.getParameter("city");
                String birthDateAsString = req.getParameter("birthDate");
                System.out.println(birthDateAsString);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate = null;
                try {
                    birthDate = formatter.parse(birthDateAsString);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                clientService.create(new Client(name, city, birthDate));
                listClients(req, resp);
                break;
                case "edit":
                    String clientId = req.getParameter("clientId");
                    String clientName = req.getParameter("name");
                    String cityName = req.getParameter("city");
                    birthDateAsString = req.getParameter("birthDate");
                    System.out.println(birthDateAsString);
                    formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        birthDate = formatter.parse(birthDateAsString);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    clientService.update(UUID.fromString(clientId), clientName, cityName, birthDate);
                    listClients(req, resp);
        }
    }

    private static void drawTable(PDPageContentStream contentStream, float y, float margin, String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float tableWidth = 500;
        final float tableHeight = 200;
        final float rowHeight = tableHeight / (float) rows;
        final float tableTopY = y;
        final float cellMargin = 5f;

        final float colWidth = tableWidth / (float) cols;

        // Draw rows
        float nextY = tableTopY;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nextY);
            contentStream.lineTo(margin + tableWidth, nextY);
            contentStream.stroke();
            nextY -= rowHeight;
        }

        // Draw columns
        float nextX = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.moveTo(nextX, tableTopY);
            contentStream.lineTo(nextX, tableTopY - tableHeight);
            contentStream.stroke();
            nextX += colWidth;
        }

        // Add text to the cells
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

        float textX = margin + cellMargin;
        float textY = tableTopY - 15;

        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                String text = content[i][j];
                contentStream.beginText();
                contentStream.newLineAtOffset(textX, textY);
                contentStream.showText(text);
                contentStream.endText();
                textX += colWidth;
            }
            textX = margin + cellMargin;
            textY -= rowHeight;
        }
    }

}
