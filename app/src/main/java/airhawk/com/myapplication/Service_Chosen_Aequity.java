package airhawk.com.myapplication;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables._AllDays;
import static airhawk.com.myapplication.Constructor_App_Variables.aequity_exchanges;
import static airhawk.com.myapplication.Constructor_App_Variables.feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_change;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_low;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_market_cap;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;

//The original version of this code was found on StackOverflow.com from user flup
//https://stackoverflow.com/users/1973271/flup
//https://stackoverflow.com/questions/21670451/how-to-send-multiple-asynchronous-requests-to-different-web-services


public class Service_Chosen_Aequity
{
    private static Context context;
    public Service_Chosen_Aequity(Context context){
        this.context=context;
    }
    public static void main()
    {

        ExecutorService service = Executors.newCachedThreadPool();
        Set <Callable<String>> callables = new HashSet <Callable<String>> ();
        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                long startTime = System.nanoTime();
                Constructor_App_Variables ax = new Constructor_App_Variables();
                String a = ax.getMarketType();
                if (a.equals("Cryptocurrency") || a.equals("Crypto"))
                {

                }
                else {
                    get_stock_shares();
                    get_stock_cap();

                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime);
                    //29 seconds Boost Mobile
                    System.out.println("GET get_stock_points TIME IS "+duration/1000000000+" seconds");
                    }


                return null;
            }
        });


        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {long startTime = System.nanoTime();
                getVideoInfo();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //1 second Boost Mobile
                System.out.println("GET VIDEOS TIME IS "+duration/1000000000+" seconds");
                return null;
            }
        });

        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {long startTime = System.nanoTime();
                ProcessXml(GoogleRSFeed());
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //4 seconds Boost Mobile
                System.out.println("GET NEWS TIME IS "+duration/1000000000+" seconds");
                return null;
            }
        });

        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {long startTime = System.nanoTime();
                Constructor_App_Variables ax = new Constructor_App_Variables();
                String a = ax.getMarketType();
                if (a.equals("Cryptocurrency") || a.equals("Crypto")){
                get_crypto_points();}
                else{
                    get_stock_points();
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //4 seconds Boost Mobile
                System.out.println("GET points IS "+duration/1000000000+" seconds");
                return null;
            }
        });


        try
        {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures)
            {
                //System.out.println (future.get());
                //Where to check all variables
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    public static void get_stock_points() {
        System.out.println("Get stock points called");
        String marname = ap_info.getMarketSymbol();
        Document d = null;
        try {
            d = Jsoup.connect("https://finance.yahoo.com/quote/" + marname + "/history?p=" + marname).timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element u = d.getElementById("Lead-2-QuoteHeader-Proxy");
        Elements x = u.select("div>span");

        String c = x.get(2).text();
        String cuap = c.replace(",","");
        ap_info.setCurrent_Aequity_Price(cuap);
        String c3 = x.get(3).text();
        String[] spit = c3.split(" ");
        spit[1]=spit[1].replaceAll("\\(","");
        spit[1]=spit[1].replaceAll("\\)","");
        ap_info.setCurrent_Aequity_Price_Change(spit[1]);

        ArrayList<String> temp = new ArrayList();
        Elements tables = d.select("table");
        Elements trs = tables.select("tr");
        for (Element g : trs) {
            Elements p = g.select("td");
            temp.add(p.text());

        }
        temp.remove(0);
        for (int counter = 0; counter < temp.size(); counter++) {
            String sev = temp.get(counter);
            if (sev.contains("Dividend")) {
            } else {
                String[] split = sev.split(" ");
                graph_date.add(split[0] + " " + split[1] + " " + split[2]);
                //System.out.print("TACOS " + split[7]);

                if (split[7].equals("adjusted")) {
                    graph_high.add(ap_info.getCurrent_Aequity_Price());
                } else {
                    if (split[7].contains("-")) {
                    } else {
                        graph_high.add(split[7]);
                    }

                }
                //System.out.println("GRAPH HIGH LIST "+graph_high);
                if (split[8].equals("for") || split[8].contains("-")) {
                    graph_volume.add("0");
                } else {
                    graph_volume.add(split[8]);
                }

            }
            //System.out.println(Arrays.asList(graph_date.get(counter))+" "+graph_high.get(counter)+" "+graph_volume.get(counter));
            List<String> numbers = graph_high;
            //Collections.reverse(numbers);
            _AllDays = numbers;
        }}
    public static void get_crypto_points() {
        long startTime = System.nanoTime();
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();
        String f = ap_info.getMarketName();
        if(ap_info.getMarketName().equalsIgnoreCase("bitcoin")||
                ap_info.getMarketName().equalsIgnoreCase("ethereum")||
                ap_info.getMarketName().equalsIgnoreCase("litecoin")||
                ap_info.getMarketName().equalsIgnoreCase("bitcoin cash")||
                ap_info.getMarketName().equalsIgnoreCase("ethereum classic")){
            aequity_exchanges.add("Coinbase");
        }

        graph_high.add(ap_info.getCurrent_Aequity_Price());
        System.out.println("NAMD!!! "+f);
        if (f.contains(" ")){
            f= f.replaceAll(" ","-");}
            Document d= null;
        String url = "https://coinmarketcap.com/currencies/" + f + "/historical-data/?start=20000101&end=" + sdf.format(begindate);
        try {
            d = Jsoup.connect(url).timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

                            Element price = d.getElementById("quote_price");
                            String v = price.text();
                            Elements divs = d.select("table");
                            for (Element tz : divs) {
                                Elements tds = tz.select("td");
                                Elements s = tz.getElementsByClass("text-right");
                                for (Element ss : s) {
                                    Elements p = ss.select("td[data-format-fiat]");
                                    String g = p.text();
                                    String[] splited = g.split("\\s+");
                                    if (v != null && !g.isEmpty()) {
                                        graph_high.add(splited[3]);
                                        graph_low.add(splited[2]);
                                    }
                                    Elements pn = ss.select("td[data-format-market-cap]");
                                    String vp = pn.text();
                                    String[] split = vp.split("\\s+");
                                    if (vp != null && !vp.isEmpty()) {
                                        graph_volume.add(split[0]);
                                        graph_market_cap.add(split[1]);
                                    }
                                }
                                for (Element bb : tds) {
                                    Elements gdate = bb.getElementsByClass("text-left");
                                    String result0 = gdate.text();
                                    if (result0 != null && !result0.isEmpty()) {
                                        graph_date.add(String.valueOf(result0));
                                    }
                                }
                            }
                            //System.out.println("THIs Is graph high "+ Arrays.asList(graph_high));
                            _AllDays = graph_high;




        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("CRYPTO POINTS TIME IS " + duration / 1000000000 + " seconds");



    }

    public static void get_stock_shares(){
        Document doc =null;
        String marname = ap_info.getMarketSymbol();
        try {
            doc = Jsoup.connect("https://finance.yahoo.com/quote/"+marname+"/key-statistics?p="+marname).timeout(10 * 1000).get();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements e =doc.select("tbody");
        Element f =e.get(8);
        Elements c =f.select("td");
        Element p =c.get(5);
        ap_info.setMarketSupply(p.text());
    }
    public static void get_stock_cap(){
        String marname = ap_info.getMarketSymbol();
        Document cap =null;
        try{
            cap =Jsoup.connect("https://finance.yahoo.com/quote/"+marname+"?p="+marname).timeout(10 *10000).get();
        } catch (IOException e){
            e.printStackTrace();
        }
        Elements ez =cap.select("td[data-test]");
        ap_info.setMarketCap(ez.get(8).text());

    }
    public static void getVideoInfo() {


        String url = "https://www.youtube.com/results";

        String begin_url;
        String mid_url;
        String end_url;
        String url_1st = "https://i.ytimg.com/vi/";
        String url_3rd = "/hqdefault.jpg";
        String market_combo = ap_info.getMarketName()+"+"+ap_info.getMarketType();
        try {
            Document doc = Jsoup.connect(url).data("search_query", market_combo).userAgent("Mozilla/5.0").timeout(10 * 10000).get();

            for (Element a : doc.select(".yt-lockup-title > a[title]")) {
                begin_url = (a.attr("href") + " " + a.attr("title"));
                begin_url = begin_url.replace("/watch?v=", "");
                mid_url = begin_url.substring(0, begin_url.indexOf(" "));
                end_url = begin_url.replace(mid_url, "");
                String f_url = url_1st + mid_url + url_3rd;
                Constructor_App_Variables.video_title.add(end_url);
                Constructor_App_Variables.video_url.add(mid_url);
                Constructor_App_Variables.image_video_url.add(f_url);
            }

        } catch (IOException e) {
            e.printStackTrace();
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

                    feedItems.add(it);


                }
            }
        }
    }
    public static org.w3c.dom.Document GoogleRSFeed() {
        try {
            URL url;
            Context context;
            String address;
            String type =ap_info.getMarketType();
            String repo = ap_info.getMarketName();
            if (repo.contains(" "))
            {
                repo =repo.replaceAll(" ","%20");
            }
            String repo_type = repo+"%20"+type;
            if (type.equals("Cryptocurrency")){
                address = "https://news.google.com/news/rss/search/section/q/" + repo_type + "?ned=us&gl=US&hl=en";

            }else {
                address = "https://news.google.com/news/rss/search/section/q/" + repo + "?ned=us&gl=US&hl=en";
            }
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

}
