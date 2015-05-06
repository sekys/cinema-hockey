import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created by Seky on 5. 5. 2015.
 */
public final class Configuration {
    public static final String URL =
        "";

    public static final int GOALS = 0;

    public static final Header[] HEADERS = new Header[] {
        new BasicHeader("Accept", "*/*"),
        new BasicHeader("Accept-Encoding", "gzip, deflate"),
        new BasicHeader("Accept-Language", "sk-SK,sk;q=0.8,cs;q=0.6,en-US;q=0.4,en;q=0.2,de;q=0.2"),
        new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
        new BasicHeader("Cache-Control", "no-cache"),
        new BasicHeader("Pragma", "no-cache"),
        new BasicHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36")
    };

    public static final CinemaRequest.Data[] USERS = new CinemaRequest.Data[] {

    };
}
