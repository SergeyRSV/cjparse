import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class proxyConnect {

    public static ArrayList proxyList() throws IOException {
        Document doc = Jsoup.connect("http://www.ip-adress.com/proxy_list/").userAgent(
                "Chrome/4.0.249.0 Safari/532.5").get();

        Elements proxyList = doc.getElementsByAttributeValue("class", "htable proxylist");

        ArrayList proxy = new ArrayList();

        for (Element elem : proxyList.select("tr")) {
            proxy.add(elem.child(0).text());
        }

        proxy.remove(0);

        return proxy;
    }


}
