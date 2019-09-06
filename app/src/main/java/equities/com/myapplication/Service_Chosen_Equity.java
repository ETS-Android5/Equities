package equities.com.myapplication;

import android.content.Context;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static equities.com.myapplication.Activity_Markets_Main.ap_info;
import static equities.com.myapplication.Constructor_App_Variables.*;

public class Service_Chosen_Equity
{
    private static Context context;

    public Service_Chosen_Equity(Context context){
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
                Constructor_App_Variables ax = new Constructor_App_Variables();
                String a = ax.getMarketType();
                if (a.equals("Cryptocurrency") || a.equals("Crypto"))
                {
                }
                else {
                    get_stock_shares();
                    get_stock_cap();
                    }


                return null;
            }
        });
        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
               getWebsite();
                return null;
            }
        });

        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                getVideoInfo();
                return null;
            }
        });

        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                ProcessXml(GoogleRSFeed());
                return null;
            }
        });

        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                Constructor_App_Variables ax = new Constructor_App_Variables();
                String a = ax.getMarketType();
                saved_helper=1;
                if (a.equals("Cryptocurrency") || a.equals("Crypto")){
                get_crypto_info();}
                else{
                get_stock_points();
                }

                return null;
            }
        });


        try
        {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures)
            {
                //// (future.get());
                //Where to check all variables
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    public static void updateFinancialData(){
        Constructor_App_Variables app_info =new Constructor_App_Variables();
        Document caps = null;
        String name=null;
        if(app_info.getMarketType()=="Crypto"||app_info.getMarketType()=="Cryptocurrency"){

            if(ap_info.getMarketName().equalsIgnoreCase("XRP")){
                name = "Ripple";
            }else{
                name = app_info.getMarketName();}

            try {
                caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + name).userAgent("Opera").timeout(10 * 10000).get();
                Element as = caps.getElementsByClass("details-panel-item--header flex-container").first();
                Element az = caps.getElementsByClass( "details-panel-item--marketcap-stats flex-container").first();
                Elements e = as.select("span:eq(1)");
                Elements p = as.select("span:eq(0)");
                String change = e.get(2).text();
                String price = p.get(1).text();
                change = change.replaceAll("\\(", "").replaceAll("\\)", "");
                current_percentage_change.clear();
                current_updated_price.clear();
                current_percentage_change.add(change);
                current_updated_price.add(price);
                Elements ee = az.select("span:eq(0)");
                String changed_volume= ee.get(4).text();
                app_info.setCurrent_volume(changed_volume);
            } catch (IOException e) {
                //  !!!!!!! !!!!!! NEED A BACKUP WEBSITE TO RETRIEVE DATA IF COINMARKETCAP IS UNAVALIABLE
                e.printStackTrace();
            }
        }else{
            try {
                caps = Jsoup.connect("https://money.cnn.com/quote/quote.html?symb="+name).userAgent("Opera").timeout(10 * 10000).get();
                Element tb= caps.select("tbody").get(1);
                String td = tb.select("td").get(7).text();
                Element tb1= caps.select("tbody").get(0);
                String td0 = String.valueOf(tb1.select("td").get(0).select("span").text());
                String td1 = String.valueOf(tb1.select("td").get(1).select("span").get(4).text());
                current_percentage_change.clear();
                current_updated_price.clear();
                current_percentage_change.add(td1);
                current_updated_price.add(td0);
                app_info.setCurrent_volume(td);
            }catch (IOException e){
                //NEED A BACKUP for stockupdate
            }

        }
    }

    public static void get_stock_points() {
        //("Get stock points called");
        String marname = ap_info.getMarketSymbol();
        Document d = null;
        try {
            d = Jsoup.connect("https://finance.yahoo.com/quote/"+marname+"/history?p="+marname).userAgent("Opera").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements x = d.getElementsByAttributeValue("class","BdT Bdc($c-fuji-grey-c) Ta(end) Fz(s) Whs(nw)");


        for(int z =0; z < x.size();z++){
            String text =x.get(z).text();
            if(text.contains("Split")||text.contains("Dividend")){
                continue;
            }
                String date =x.get(z).select("td").get(0).text();
                graph_date.add(date);
                String close =x.get(z).select("td").get(5).text();
                graph_high.add(close.replace(",",""));
                String volume =x.get(z).select("td").get(6).text();
                graph_volume.add(volume);
                List<String> numbers = graph_high;
                //Collections.reverse(numbers);
                _AllDays = numbers;}
        System.out.println("THIS IS THE GRAPH YAHOO "+ graph_high.size());

        //(graph_high.size());
    }

    public static void get_crypto_info() {
        long startTime = System.nanoTime();
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();
        String f=null;
        if(ap_info.getMarketName().equalsIgnoreCase("XRP")){
            f = "Ripple";
        }else {
            f=ap_info.getMarketName();
        }
        if(ap_info.getMarketName().equalsIgnoreCase("bitcoin")||
                ap_info.getMarketName().equalsIgnoreCase("ethereum")||
                ap_info.getMarketName().equalsIgnoreCase("litecoin")||
                ap_info.getMarketName().equalsIgnoreCase("bitcoin cash")||
                ap_info.getMarketName().equalsIgnoreCase("ethereum classic")){
            aequity_exchanges.add("Coinbase");
        }

        graph_high.add(ap_info.getCurrent_Aequity_Price());
        //("NAMD!!! "+f);
        if (f.contains(" ")){
            f= f.replaceAll(" ","-");}
            Document d= null;
        String url = "https://coinmarketcap.com/currencies/" + f + "/historical-data/?start=20000101&end=" + sdf.format(begindate);
        try {
            d = Jsoup.connect(url).userAgent("Opera").timeout(10 * 10000).get();
        } catch (IOException e) {
            try{
                String[] s = f.split("(?=\\p{Lu})");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
                                        graph_high.add(splited[0].replaceAll("[^\\d.]",""));
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
                            ////("THIs Is graph high "+ Arrays.asList(graph_high));
                            _AllDays = graph_high;
        if (graph_high.size()==0){graph_high.add(0);}



        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //("CRYPTO POINTS TIME IS " + duration / 1000000000 + " seconds");



    }

    public static void get_stock_shares(){
        Document doc =null;
        String marname = ap_info.getMarketSymbol();
        try {
            doc = Jsoup.connect("https://finance.yahoo.com/quote/"+marname+"/key-statistics?p="+marname).userAgent("Opera").timeout(10 * 1000).get();
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
            cap =Jsoup.connect("https://finance.yahoo.com/quote/"+marname+"?p="+marname).userAgent("Opera").timeout(10 *10000).get();
            Elements ez =cap.select("td[data-test]");
            ap_info.setMarketCap(ez.get(8).text());
            ap_info.setCurrent_volume(ez.get(6).text());
        } catch (IOException e){
            //Backup method
            e.printStackTrace();
        }


    }

    public static void getWebsite(){
        Constructor_App_Variables app_info =new Constructor_App_Variables();
        Document caps = null;
        String name=app_info.getMarketName();
        if(app_info.getMarketType()=="Crypto"||app_info.getMarketType()=="Cryptocurrency"){
            try{
                caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + name).userAgent("Opera").timeout(10 * 10000).get();
                Elements links = caps.getElementsByClass("list-unstyled details-panel-item--links");
                Element link =links.select("li>a").get(0);
                String url = link.attr("href");
                app_info.setChosen_website(url);
            } catch (IOException e) {
                try{
                    //Alternative crypto site
                    caps = Jsoup.connect("https://www.coingecko.com/en/coins/" + name).userAgent("Opera").timeout(10 * 10000).get();
                    Elements links = caps.getElementsByClass("col-md-9 col-lg-7 p-0");
                    Elements link =links.select("div>a");
                    String url = link.attr("href");
                    app_info.setChosen_website(url);
                }catch (IOException x){
                    app_info.setChosen_website("Unknown");
                }

            }
        }else {
            try {
                //Get stock website
                caps = Jsoup.connect("https://finance.yahoo.com/quote/" + name + "/profile?p=" + name).timeout(10 * 10000).get();
                Element main = caps.getElementById("Main");
                String d = main.select("a[href]").get(0).text();
                app_info.setChosen_website(d);
            } catch (IOException e) {
                //Use a different website
                try {
                    caps = Jsoup.connect("https://money.cnn.com/quote/profile/profile.html?symb=" + name).timeout(10 * 10000).get();
                    Element tb = caps.select("tbody").get(3);
                    String d = tb.select("a[href]").text();
                    app_info.setChosen_website(d);

                } catch (IOException e1) {
                    app_info.setChosen_website("Unknown");
                }
            }
        }}

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
        //world_markets_news_feedItems.clear();
        if (data != null) {
            String st, sd, sp, sl,si;
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
                            st = curent.getTextContent().toString();
                            st= st.substring(0,st.indexOf(" - ")+" - ".length());
                            st= st.replace("-","");
                            it.setTitle(st);
                            //(st);
                        } else if (curent.getNodeName().equalsIgnoreCase("media:content")) {
                            sd = curent.getTextContent().toString();
                            //(sd);

                            String d = curent.getTextContent().toString();
                            String pattern1 = "<img src=\"";
                            String pattern2 = "\"";
                            Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
                            Matcher m = p.matcher(d);
                            while (m.find()) {
                                it.setThumbnailUrl(sd);
                                ////("HERE IS YOUR IMAGE DUDE! "+m.group(1));
                            }
                            it.setDescription(sd);
                        } else if (curent.getNodeName().equalsIgnoreCase("pubDate")) {
                            sp = curent.getTextContent().toString();
                            sp = sp.replaceAll("@20", " ");
                            it.setPubDate(sp);
                            //(sp);
                        } else if (curent.getNodeName().equalsIgnoreCase("link")) {
                            sl = curent.getTextContent().toString();
                            sl = sl.replaceAll("@20", " ");
                            it.setLink(sl);
                            //(sl);
                        } else if (curent.getNodeName().equalsIgnoreCase("source")) {
                            si = curent.getTextContent().toString();
                            it.setSource(si);
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
            String f = ap_info.getMarketName();
            String g =ap_info.getMarketType();
            String address = "https://news.google.com/news/rss/search/section/q/" + f +"%20"+g+ "?ned=us&gl=US&hl=en";


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
