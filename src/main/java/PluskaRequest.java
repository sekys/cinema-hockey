import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;

/**
 * Created by Seky on 5. 5. 2015.
 */
public final class PluskaRequest extends HttpGet {
    public PluskaRequest() {
        super(Configuration.URL);

        int timeout = 2;
        setHeaders(Configuration.HEADERS);
        setConfig(RequestConfig.custom()
            .setSocketTimeout(timeout * 1000)
            .setConnectTimeout(timeout * 1000)
            .build());
    }
}
