package airhawk.com.myapplication;

import android.app.Service;
import android.content.Context;
import android.text.Html;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static airhawk.com.myapplication.Constructor_App_Variables.all_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.alt_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_name;
import static airhawk.com.myapplication.Constructor_App_Variables.all_feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_change;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_name;
import static airhawk.com.myapplication.Constructor_App_Variables.nd_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_change;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_name;

//The original version of the core code was found on StackOverflow.com from user flup
//https://stackoverflow.com/users/1973271/flup
//https://stackoverflow.com/questions/21670451/how-to-send-multiple-asynchronous-requests-to-different-web-services


public class Service_Main_Aequities {
    public static Context myContext;
    public Service_Main_Aequities(Context context)
    {
        myContext = context;
    }
    public Service_Main_Aequities(){}
    static Document sss = null;
    static Document sv = null;
    static Document z = null;
    static Document bb;
    static Document stock_data;
    static Document crypto_data;
    static ArrayList stock_kings_symbollist = new ArrayList();
    static ArrayList stock_kings_namelist = new ArrayList();
    static ArrayList stock_kings_changelist = new ArrayList();
    static ArrayList stock_win_symbollist = new ArrayList();
    static ArrayList stock_win_namelist = new ArrayList();
    static ArrayList stock_win_changelist = new ArrayList();
    static ArrayList stock_losers_symbollist = new ArrayList();
    static ArrayList stock_losers_namelist = new ArrayList();
    static ArrayList stock_losers_changelist = new ArrayList();
    public static ArrayList crypto_volume_namelist = new ArrayList();
    public static ArrayList crypto_volume_volumelist = new ArrayList();
    static ArrayList crypto_winners_changelist = new ArrayList();
    static ArrayList crypto_winners_namelist = new ArrayList();
    static ArrayList crypto_winners_symbollist = new ArrayList();
    static ArrayList crypto_losers_changelist = new ArrayList();
    static ArrayList crypto_losers_namelist = new ArrayList();
    static ArrayList crypto_losers_symbollist = new ArrayList();
    static ArrayList crypto_kings_symbolist = new ArrayList();
    static ArrayList crypto_kings_namelist = new ArrayList();
    static ArrayList crypto_kings_marketcaplist = new ArrayList();
    static ArrayList crypto_kings_changelist =new ArrayList();

    public static void main() {
        ExecutorService service = Executors.newCachedThreadPool();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();


        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getStocks_Market_Caps();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //17 seconds no wifi Boost Mobile RIDICULOUS!
                //2 Seconds on wifi
                System.out.println("getStocks_Market_Caps TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });



        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getCrypto_Winners_Losers();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //2 seconds wifi
                //21 seconds Boost Mobile
                System.out.println("getCrypto_Winners_Losers TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });





        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getStock_Winners_Losers();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //2 seconds wifi
                //2 seconds Boost Mobile
                System.out.println("getStock_Winners_Losers TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getStock_Kings();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //wifi 1 seconds
                //4 seconds Boost Mobile
                System.out.println("getStock_Winners_Losers TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                ProcessXml(GoogleRSFeed());
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //wifi 1 second
                //Boost Mobile 5 seconds
                System.out.println("getStock_News TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });





        try {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures) {
//                System.out.println(future.get());
                //Where to check all variables
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getStock_Winners_Losers() {
        try {
            sss = Jsoup.connect("http://money.cnn.com/data/hotstocks/").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements ddd = sss.getElementsByClass("wsod_dataTable wsod_dataTableBigAlt");
        Element ff = ddd.get(1);
        Elements a = ff.select("a");
        Elements b = ff.select("span[title]");
        Elements aa = ff.select("span.posData");
        for (Element stock_symbol : a) {
            String symbol = stock_symbol.select("a.wsod_symbol").text();
            stock_win_symbollist.add(symbol);
        }
        for (Element stock_name : b) {
            String name = stock_name.text();
            if (name.isEmpty()) {
            } else {
                stock_win_namelist.add(name);
            }
        }
        for (Element stock_change : aa) {
            String change = stock_change.text();
            if (change.isEmpty()) {
            } else {
                if (change.contains("%")) {
                    stock_win_changelist.add(change);
                }

            }
        }
//STOCK LOSERS ARRAYS
        Element fl = ddd.get(2);
        Elements al = fl.select("a");
        Elements bl = fl.select("span[title]");
        Elements aal = fl.select("span.negData");
        for (Element x : al) {
            stock_losers_symbollist.add(x.text());
        }
        for (Element x : bl) {
            stock_losers_namelist.add(x.text());
        }
        for (int i = 0; i < aal.size(); i++) {
            if (i % 2 == 0) {
                // This is point amount
            } else {
                stock_losers_changelist.add(aal.get(i).text());
            }
        }

    }

    private static void ProcessXml(org.w3c.dom.Document data) {
        if (data != null) {
            String st, sd, sp, sl;
            org.w3c.dom.Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(0);
            NodeList items = channel.getChildNodes();

            for (int i = 0; i < items.getLength(); i++) {
                Constructor_News_Feed it = new Constructor_News_Feed();
                Node curentchild = items.item(i);
                if (curentchild.getNodeName().equalsIgnoreCase("item")) {
                    NodeList itemchilds = curentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node curent = itemchilds.item(j);
                        if (curent.getNodeName().equalsIgnoreCase("title")) {
                            st = Html.fromHtml(curent.getTextContent()).toString();
                            it.setTitle(st);
                        } else if (curent.getNodeName().equalsIgnoreCase("description")) {
                            sd = Html.fromHtml(curent.getTextContent()).toString();
                            String d = curent.getTextContent().toString();
                            String pattern1 = "<img src=\"";
                            String pattern2 = "\"";
                            Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
                            Matcher m = p.matcher(d);
                            while (m.find()) {
                                it.setThumbnailUrl(m.group(1));
                            }
                            it.setDescription(sd);

                        } else if (curent.getNodeName().equalsIgnoreCase("pubDate")) {
                            sp = Html.fromHtml(curent.getTextContent()).toString();
                            sp = sp.replaceAll("@20", " ");
                            it.setPubDate(sp);
                        } else if (curent.getNodeName().equalsIgnoreCase("link")) {
                            sl = Html.fromHtml(curent.getTextContent()).toString();
                            sl = sl.replaceAll("@20", " ");
                            it.setLink(sl);
                        } else if (curent.getNodeName().equalsIgnoreCase("img src")) {

                        }
                    }

                    all_feedItems.add(it);


                }
            }
        }
    }
    public static org.w3c.dom.Document GoogleRSFeed() {
        try {
            URL url;
            Context context;
            String repo = "Stock%20Cryptocurrency";
            String address = "https://news.google.com/news/rss/search/section/q/" + repo + "?ned=us&gl=US&hl=en";


            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    //16
    //15
    public static void getStocks_Market_Caps() {
        try {
            z = Jsoup.connect("https://finance.yahoo.com").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table = z.getElementById("Lead-2-FinanceHeader-Proxy");
        Elements as = table.select("a[title]");
        sp_name = as.get(0).text().replace("&", "");
        dow_name = as.get(2).text();
        nas_name = as.get(4).text();
        Elements as1 = z.select("h3>span");
        sp_amount =as1.get(0).text();
        dow_amount=as1.get(1).text();
        nas_amount=as1.get(2).text();
        Elements as2 =z.select("span>span");
        sp_change =as2.get(1).text();
        dow_change=as2.get(2).text();
        nas_change=as2.get(3).text();
        if (sp_change.contains("-")){
            sp_flow=true;
        }
        if (dow_change.contains("-")){
            dow_flow=true;
        }
        if (nas_change.contains("-")){
            nd_flow=true;
        }

    }
    //7
    public static void getCrypto_Winners_Losers() {
        try {


            stock_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements ffsd = stock_data.getElementsByClass("table-responsive");
        Elements tr = stock_data.select("div#losers-24h");
        Elements lose1 = tr.select("td.text-left");
        Elements lose2 = tr.select("td[data-sort]");
        for (Element s : lose1) {
            crypto_losers_symbollist.add(s.text());
        }
        for (int i = 0; i < lose2.size(); i++) {
            if (i % 2 == 0) {
                crypto_losers_namelist.add(lose2.get(i).text());
            } else {
                crypto_losers_changelist.add(lose2.get(i).text());
            }
        }
//CRYPTO WINNERS ARRAYS
        Element l = ffsd.get(2);
        Elements xx = l.select("tbody");
        Elements xxxd = xx.select("td[data-usd]");//Get's percentage change
        Elements ax = xx.select("tr");
        Elements name_change = xx.select("img[src]");
        for (Element crypto_symbol : ax) {
            String symbol = crypto_symbol.select("td.text-left").text();
            crypto_winners_symbollist.add(symbol);
        }
        for (Element crypto_name : name_change) {
            String name = crypto_name.attr("alt");
            crypto_winners_namelist.add(name);
        }
        for (Element crypto_change : xxxd) {
            crypto_winners_changelist.add(crypto_change.text());
        }

    }

    public static void getStock_Kings() {
        Document sv = null;
        try {
            sv = Jsoup.connect("http://www.iweblists.com/us/commerce/MarketCapitalization.html").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements table_body = sv.select("tr td:eq(1)");
        Elements table_body2 = sv.select("tr td:eq(2)");
        Elements table_body3 = sv.select("tr td:eq(3)");
        for(int i=1; i<=10;i++)
        {
            stock_kings_namelist.add(table_body.get(i).text());
            stock_kings_symbollist.add(table_body2.get(i).text());
            Double add = Double.parseDouble(table_body3.get(i).text());
            String added =null;
            if (add >1000){
                added =table_body3.get(i).text()+" T";
            }else{
                added =table_body3.get(i).text()+" B";
            }
            stock_kings_changelist.add(added);
        }

    }

    public void getSaved_Crypto_Price() {

        Database_Local_Aequities db = new Database_Local_Aequities(myContext);
        db.getName();
        for (int i = 0; i <= db.getName().size(); i++) {
            try {
                bb = Jsoup.connect("https://coinmarketcap.com/currencies/" +db.getName().get(i)).timeout(10 * 10000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Connect_Stocktwits(){}

}
