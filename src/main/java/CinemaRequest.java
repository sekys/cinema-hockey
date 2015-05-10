import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seky on 5. 5. 2015.
 */
public final class CinemaRequest extends HttpPost {
    public static class Data {
        public String meno;
        public String priezvisko;
        public String email;
        public String telefon;

        public Data(String meno, String priezvisko, String email, String telefon) {
            this.meno = meno;
            this.priezvisko = priezvisko;
            this.email = email;
            this.telefon = telefon;
        }
    }

    public CinemaRequest(String url) {
        super(url);

        int timeout = 3;
        setHeaders(Configuration.HEADERS);
        setConfig(RequestConfig.custom()
            .setSocketTimeout(timeout * 1000)
            .setConnectTimeout(timeout * 1000)
            .build());
    }

    public CinemaRequest(String url, String nahoda, Data data) throws UnsupportedEncodingException {
        this(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("nahoda", nahoda));
        urlParameters.add(new BasicNameValuePair("souhlas", "1"));
        urlParameters.add(new BasicNameValuePair("jmeno", data.meno));
        urlParameters.add(new BasicNameValuePair("prijmeni", data.priezvisko));
        urlParameters.add(new BasicNameValuePair("email", data.email));
        urlParameters.add(new BasicNameValuePair("telefon", data.telefon));
        setEntity(new UrlEncodedFormEntity(urlParameters));
    }
}
