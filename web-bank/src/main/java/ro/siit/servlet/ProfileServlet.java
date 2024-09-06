package ro.siit.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import ro.siit.entity.PersonWithAge;

import java.io.IOException;

@WebServlet(urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/profile/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        req.setAttribute("title", title);


        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet("https://api.agify.io/?name=" + title);
            // httpGet.setHeader("API-KEY / apiKey ? ", System.getenv().get("MY-key") / "34987r5y243priuchxeufnhwcfuwq ");
            final String result =  httpClient.execute(httpGet, response  -> {
                System.out.println(response);
                return EntityUtils.toString(response.getEntity());
            });

            Gson gson = new Gson();
            PersonWithAge personWithAge = gson.fromJson(result, PersonWithAge.class);

            req.setAttribute("personInfo", personWithAge);
        };


        req.getRequestDispatcher("/jsps/profile/profile.jsp").forward(req, resp);

    }
}
