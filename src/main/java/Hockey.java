import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * Created by Seky on 5. 5. 2015.
 */
public final class Hockey {
    private static final Logger LOGGER = Logger.getLogger(Hockey.class);
    private final HttpClient client;

    public Hockey() {
        client = HttpClientBuilder.create().build();
    }

    private void crawl() throws Exception {
        // Build request
        HttpGet request = new PluskaRequest();
        HttpResponse response = client.execute(request);
        StringBuilder data = new StringBuilder();

        // Add HTML elements to fix issue with invalid format
        String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        data.append("<html><head><title>First responseparse</title></head><body>");
        data.append(result);
        data.append("</body></html>");

        // Parse document
        Document doc = Jsoup.parse(data.toString());
        parseDocument(doc);
    }


    private void parseDocument(Document doc) throws Exception {
        Elements links = doc.select(".cinema");
        if (links.size() <= Configuration.GOALS) {
            return;
        }
        Element tagA = links.get(0);
        String href = tagA.attr("href");
        if (href == null) {
            return;
        }
        foundNewLink(href);
    }

    private void playAlert() {
        openBrowser("https://www.youtube.com/watch?v=jRxOcefjD54", 1);
    }

    private void openBrowser(String href, int tabs) {
        try {
            for (int i = 0; i < tabs; i++) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI(href));
                }
            }
        } catch (Exception e) {
        }
    }

    private void foundNewLink(String href) {
        openBrowser(href, 1);
        registerUsers(href);
        playAlert();
        LOGGER.info("Finished.");
        System.exit(0);
    }

    private void registerUsers(String url) {
        for (CinemaRequest.Data user : Configuration.USERS) {
            try {
                registerUser(url, user);
            } catch (Exception e) {
                LOGGER.error("registerUsers", e);
            }
        }
    }

    private void registerUser(String url, CinemaRequest.Data user) throws Exception {
        // Prepare client
        HttpClient httpClient = HttpClientBuilder.create().build();
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        // Select nahoda attribute
        HttpResponse response = httpClient.execute(new CinemaRequest(url), httpContext);
        String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        //result = FileUtils.readFileToString(new File("source-code.txt"));
        Document doc = Jsoup.parse(result);
        Elements inputs = doc.select("input[name=nahoda]");
        if (inputs.isEmpty()) {
            LOGGER.warn("ziadny input tag");
            return;
        }
        Element nahodaInput = inputs.get(0);
        String nahoda = nahodaInput.attr("value");
        if (nahoda == null) {
            LOGGER.warn("ziadny input nahoda");
            return;
        }

        // Send data
        response = httpClient.execute(new CinemaRequest(url, nahoda, user), httpContext);
        result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File("result/" + user.email + ".txt"), result);
    }

    public void run() {

        try {
            //registerUser("",Configuration.USERS[1]);
            // registerUser("http://myhttp.info", Configuration.USERS[1]);
            int seconds = 1;
            while (true) {
                crawl();
                Thread.sleep(seconds * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Hockey hockey = new Hockey();
        hockey.run();
    }
}
