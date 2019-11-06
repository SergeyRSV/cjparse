import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Main {

    private static ArrayList proxyList;
    private static String proxyArr;
    private static String host;
    private static int port;
    static Proxy proxy;

    public static void main(String[] args) throws IOException {

        getProxy();
        getHtml(1);
//        getInfo();

    }


    public static void getProxy() throws IOException {

        proxyList = proxyConnect.proxyList();

        boolean loop = true;
        while (loop == true) {
            proxyArr = (String) proxyList.get(0);
            host = (proxyArr.split(":")[0]);
            port = Integer.parseInt(proxyArr.split(":")[1]);
            System.out.println(proxyArr);
            System.out.println("Connected");

//            Proxy proxy1 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("134.209.162.5", 80));
//            URLConnection conn = new URL("https://2ip.ru/").openConnection(proxy1);


            try {
                proxy = new Proxy(
                        Proxy.Type.HTTP,
                        InetSocketAddress.createUnresolved(host, port));
                Document doc = Jsoup
                        .connect("https://cian.ru/")
//                        .proxy(proxy)
                        .timeout(5000)
                        .userAgent(
                                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; " +
                                        "ru-RU; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                        .get();
                Elements noCaptcha = doc.getElementsByAttributeValue(
                        "class", "_025a50318d--c-filters-form-buttons--2SF4G");
                if (noCaptcha.size() == 0) {
                    System.out.println("Captcha block");
                    proxyList.remove(0);
                    continue;
                }
                System.out.println(noCaptcha.text());

                loop = false;
            } catch (Exception e) {
                System.out.println("Bad");
                proxyList.remove(0);
            }
        }

    }

    public static void getHtml(int page) throws IOException {
        String URL = "https://krasnogorsk.cian.ru/cat.php?deal_type=sale&engine_version=2&offer_type=flat&p=" +
                page + "&region=175071&room1=1&room2=1";


        Document doc = Jsoup
                .connect(URL)
//                .proxy(proxy)
                .timeout(15000)
                .userAgent(
                        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; " +
                                "ru-RU; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                .get();

//        System.out.println(doc);
//        System.out.println("******************************************");
        Elements pageLink = doc.getElementsByAttributeValue("class", "_93444fe79c--wrapper--E9jWb").attr("class", "c6e8ba5398--header--1fV2A");
//        Elements pageLink = doc.getElementsByClass("c6e8ba5398--header--1fV2A");
//        Elements links = pageLink.select("href");
//        System.out.println(pageLink);
        for (Element elem : pageLink.select("div")) {
            System.out.println(elem);
            System.out.println("-------------------------------------------------------");
        }
//        System.out.println(pageLink.);

//                for (Element elem : pageLink.select("div")) {
//                    System.out.println(elem.child(2));
//            System.out.println("-------------------------------------------------------");
//        }
    }

    public static void getInfo() throws IOException {

        Document doc = Jsoup.connect(
                "https://krasnogorsk.cian.ru/sale/flat/209784676/").proxy(
                "127.0.0.1", 8080).userAgent(
                "Chrome/4.0.249.0 Safari/532.5").get();
        Elements title = doc.getElementsByAttributeValue("class", "a10a3f92e9--title--2Widg");
        Elements FullAddr = doc.getElementsByAttributeValue("class", "a10a3f92e9--address--140Ec");

        System.out.println(title.text());
        for (Element elem : FullAddr.select("a")) {
            System.out.println(elem.text());
        }

    }
}


