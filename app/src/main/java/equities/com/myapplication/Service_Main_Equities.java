package equities.com.myapplication;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static equities.com.myapplication.Constructor_App_Variables.*;

public class Service_Main_Equities {
    public static Context myContext;

    public Service_Main_Equities(Context context) {
        myContext = context;
    }

    public Service_Main_Equities() {
    }
    static Integer[] lowest_integer;
    static Document sss = null;
    static Document sv = null;
    static Document z = null;
    static Document crypto_data;
    static ArrayList top_news_list;
    static ArrayList stock_kings_symbollist = new ArrayList();
    static ArrayList stock_kings_namelist = new ArrayList();
    static ArrayList stock_kings_ipdown = new ArrayList();
    static ArrayList stock_kings_changelist = new ArrayList();
    static ArrayList stock_kings_pricelist = new ArrayList();

    static ArrayList stock_winners_symbollist = new ArrayList();
    static ArrayList stock_winners_pricelist = new ArrayList();
    static ArrayList stock_winners_namelist = new ArrayList();
    static ArrayList stock_winners_changelist = new ArrayList();

    static ArrayList crypto_winners_symbollist = new ArrayList();
    static ArrayList crypto_winners_pricelist = new ArrayList();
    static ArrayList crypto_winners_namelist = new ArrayList();
    static ArrayList crypto_winners_changelist = new ArrayList();

    static ArrayList typelist = new ArrayList();
    static ArrayList crypto_losers_pricelist = new ArrayList();
    static ArrayList crypto_losers_changelist = new ArrayList();
    static ArrayList crypto_losers_namelist = new ArrayList();
    static ArrayList crypto_losers_symbollist = new ArrayList();

    static ArrayList stock_losers_pricelist = new ArrayList();
    static ArrayList stock_losers_symbollist = new ArrayList();
    static ArrayList stock_losers_namelist = new ArrayList();
    static ArrayList stock_losers_changelist = new ArrayList();

    static ArrayList crypto_kings_pricelist = new ArrayList();

    static ArrayList crypto_kings_symbolist = new ArrayList();
    static ArrayList crypto_kings_namelist = new ArrayList();
    static ArrayList crypto_kings_marketcaplist = new ArrayList();
    static ArrayList crypto_kings_changelist = new ArrayList();

    public static void main() {
        clearMainData();
        ExecutorService service = Executors.newCachedThreadPool();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(new Callable<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                    WorldMarketsMethod();
                }
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                 //   get_masternodes();
                }
                return null;
            }
        });
        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else {
                 //   get_ipos();
                        }
                return null;
            }
        });

        try {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures) {
//                //(future.get());
                //Where to check all variables
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void WorldMarketsMethod(){
        getWorldMarkets();
        ProcessXml(GoogleRSWorldMarketsFeed());
        getVideoInfo("World Financial Markets");

    }

    public static void getVideoInfo(String term) {
        String url = "https://www.youtube.com/results";
        String begin_url;
        String mid_url;
        String end_url;
        String url_1st = "https://i.ytimg.com/vi/";
        String url_3rd = "/hqdefault.jpg";
        try {
            Document doc = Jsoup.connect(url).data("search_query", term).userAgent("Mozilla/5.0").timeout(10 * 10000).get();

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



    //Completed Method but need a backup for masternodes
    public static void get_masternodes() {
        Document m = null;
        try {
            m = Jsoup.connect("https://masternodes.online").userAgent("Mozilla").timeout(10 * 100000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element c = m.getElementById("coins");
        Elements tb = c.select("tbody");
        Elements n = tb.get(1).select("tr");
        for (int x = 0; x < n.size(); x++) {
            Constructor_Masternodes constructor_masternodes = new Constructor_Masternodes();
            Elements k = n.get(x).select("td");
            String [] splits = k.get(2).text().split(" ");
            constructor_masternodes.setMasternode_name(splits[0]);
            constructor_masternodes.setMasternode_symbol(splits[1].replace("(","").replace(")",""));
            constructor_masternodes.setMasternode_percent_change(k.get(4).text());
            Float add;
            String mc = k.get(6).text().replace("%", "").replace("$", "").replace(",", "");
            if (mc.contains("?")) {
                constructor_masternodes.setMasternode_marketcap("Unknown");
            } else {
                add = Float.parseFloat(mc);
                if (add > 999) {
                    String a = String.format("%.0f", add);
                    String added = null;

                    if (mc.length() > 9) {
                        added = a.substring(0, 3) + " B";
                    }
                    if (mc.length() <= 9) {
                        added = a.substring(0, 3) + " M";
                    }
                    if (mc.length() == 11) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 2) + "." + added.substring(2, added.length()));
                    }
                    if (mc.length() == 10) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 1) + "." + added.substring(1, added.length()));
                    }
                    ;
                    if (mc.length() == 9) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 3) + "." + added.substring(3, added.length()));
                    }
                    if (mc.length() == 8) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 2) + "." + added.substring(2, added.length()));
                    }
                    if (mc.length() == 7) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 1) + "." + added.substring(1, added.length()));
                    }
                    if (mc.length() == 6) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 3) + "." + added.substring(2, added.length()));
                    }
                }
                constructor_masternodes.setMasternode_node_count(k.get(8).text());
                constructor_masternodes.setMasternode_purchase_value(k.get(10).text());}

            masternode_feedItems.add(constructor_masternodes);


        }

    }

    public static void getMarketWinnersStock(){
        sv = null;
        try {

            sv = Jsoup.connect("https://finance.yahoo.com/gainers").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbody =sv.select("tbody");
            Elements tr =tbody.select("tr");
            for(int i=0;i<20;i++){
                String symbol = tr.get(i).select("td").get(0).text();
                String name = tr.get(i).select("td").get(1).text();
                String price = tr.get(i).select("td").get(2).text().replace("+","");
                String change = tr.get(i).select("td").get(4).text().replace("+","");
                stock_winners_symbollist.add(symbol);
                stock_winners_namelist.add(name);
                stock_winners_pricelist.add(price);
                stock_winners_changelist.add(change);
                typelist.add("Stock");


            }}catch (IOException e) {
            //Use alternative method
        }


    }
    public static void getMarketWinnersCrypto(){
        try {
            crypto_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").timeout(10 * 10000).get();
            Elements losers_table = crypto_data.getElementsByClass("table-responsive");
            Element winners_table = losers_table.get(2);
            Elements tbody = winners_table.select("tbody");
            Elements winners = crypto_data.select("div#gainers-24h");
            Elements winner_change = tbody.select("td[data-usd]");//Get's percentage change
            Elements winner_symbol = tbody.select("tr");
            Elements winner_name = tbody.select("img[src]");
            for (Element crypto_symbol : winner_symbol) {
                if(crypto_winners_symbollist.size()<20){
                    String symbol = crypto_symbol.select("td.text-left").text();
                    crypto_winners_symbollist.add(symbol);
                }
                typelist.add("Cryptocurrency");
            }
            Elements link = winners.select("a.price");
            for(Element x : link){
                if(crypto_winners_pricelist.size()<20){
                    String url = x.attr("data-usd");
                    toDouble(crypto_winners_pricelist,url);}
            }
            for (Element crypto_name : winner_name) {
                if(crypto_winners_namelist.size()<20){
                    String name = crypto_name.attr("alt");
                    crypto_winners_namelist.add(name);}
            }
            for (Element crypto_change : winner_change) {
                if(crypto_winners_changelist.size()<20){
                    crypto_winners_changelist.add(crypto_change.text());}
            }

        } catch (IOException e) {
            //Use alternative method
        }


    }
    public static void getMarketWinners(){
       getMarketWinnersStock();
       getMarketWinnersCrypto();
    }





    public static void getStockLosers(){
        try{
            sv = Jsoup.connect("https://finance.yahoo.com/losers").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbodyl = sv.select("tbody");
            Elements trl = tbodyl.select("tr");
            for (int il = 0; il < 20; il++) {
                String lsymbol = trl.get(il).select("td").get(0).text();
                String lname = trl.get(il).select("td").get(1).text();
                String lprice = trl.get(il).select("td").get(2).text().replace("+", "");
                String lchange = trl.get(il).select("td").get(4).text().replace("+", "");
                stock_losers_symbollist.add(lsymbol);
                stock_losers_namelist.add(lname);
                stock_losers_pricelist.add(lprice);
                stock_losers_changelist.add(lchange);
                // System.out.println("FIRST METHOD  LOSERS"+lsymbol + " " + lname + " " + lprice + " " + lchange);
            }

        } catch (IOException e) {
//ALTERNATIVE METHOD
            try {
                sv = Jsoup.connect("http://money.cnn.com/data/hotstocks/").userAgent("Opera").timeout(10 * 10000).get();
                Elements ddd = sv.getElementsByClass("wsod_dataTable wsod_dataTableBigAlt");
                Element ff = ddd.get(1);
                Elements a = ff.select("a");
                Elements b = ff.select("span[title]");
                Elements aa = ff.select("span.posData");
                Elements bb = ff.select("span[stream]");
                if(stock_winners_symbollist.size()<20){
                    for (Element stock_symbol : a) {
                        String symbol = stock_symbol.select("a.wsod_symbol").text();
                        stock_winners_symbollist.add(symbol); }
                    for (Element stock_name : b) {
                        String name = stock_name.text();
                        if (name.isEmpty()) {
                        } else {
                            stock_winners_namelist.add(name); } }
                    for (Element stock_price : bb) {
                        String price = stock_price.text();
                        if (price.isEmpty()) {
                        } else {
                            if (price.contains("+")||price.contains("-")||price.contains("%")) {
                            }else{
                                stock_winners_pricelist.add(price);} } }
                    for (Element stock_change : aa) {
                        String change = stock_change.text();
                        if (change.isEmpty()) {
                        } else {
                            if (change.contains("%")) {
                                stock_winners_changelist.add(change); } } }
                    //STOCK LOSERS ARRAYS
                    Element fl = ddd.get(2);
                    Elements al = fl.select("a");
                    Elements bl = fl.select("span[title]");
                    Elements aal = fl.select("span.negData");
                    Elements bb1 = fl.select("span[stream]");
                    for (Element x : al) {
                        stock_losers_symbollist.add(x.text()); }
                    for (Element x : bl) {
                        stock_losers_namelist.add(x.text()); }
                    for (Element stock_price : bb1) {
                        String price = stock_price.text();
                        if (price.isEmpty()) {
                        } else {
                            if (price.contains("+")||price.contains("-")||price.contains("%")) {
                            }else{
                                stock_losers_pricelist.add(price);} } }
                    for (int i = 0; i < aal.size(); i++) {
                        if (i % 2 == 0) {
                            // This is point amount} else {
                            stock_losers_changelist.add(aal.get(i).text()); } }
                }} catch (IOException z) {
                z.printStackTrace();
            }

            e.printStackTrace();
        }

    }
    public static void getCryptoLosers(){
        //Scrape data and if any Array is 0 use API as backup
        try {
            crypto_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").timeout(10 * 10000).get();
            Elements losers_table = crypto_data.getElementsByClass("table-responsive");
            Elements losers = crypto_data.select("div#losers-24h");
            Elements loser_symbol = losers.select("td.text-left");
            Elements loser_name_change = losers.select("td[data-sort]");
            for (Element s : loser_symbol) {
                crypto_losers_symbollist.add(s.text());
            }
            Elements price = losers.select("a.price");
            for(Element x : price){
                if(crypto_losers_pricelist.size()<20){
                    String url = x.attr("data-usd");
                    toDouble(crypto_losers_pricelist,url);}
            }
            for (int i = 0; i < loser_name_change.size(); i++) {
                if (i % 2 == 0) {
                    if(crypto_losers_namelist.size()<20){
                        crypto_losers_namelist.add(loser_name_change.get(i).text());}
                } else {
                    if(crypto_losers_changelist.size()<20){
                        crypto_losers_changelist.add(loser_name_change.get(i).text());}
                }
            }

        } catch (IOException e) {
        }

    }
    public static void getMarketLosers(){
       getCryptoLosers();
       getStockLosers();
    }


    public static void getMarketKingsStock(){
        try {
//GET STOCK KINGS
            sv = Jsoup.connect("https://www.tradingview.com/markets/stocks-usa/market-movers-large-cap/").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbody =sv.select("tbody");

            Elements tr =tbody.select("tr");
            for (Element z : tr) {
                if (stock_kings_symbollist.size() < 20) {


                    String td0 = z.select("td").get(0).text();
                    String[] split = td0.split(" ");
                    String symbol = split[0];
                    String name = td0.replace(split[0],"");
                    String td1 = z.select("td").get(1).text();
                    String price = td1;
                    String td2 = z.select("td").get(2).text();
                    String change = td2;
                    stock_kings_symbollist.add(symbol);
                    stock_kings_namelist.add(name);
                    stock_kings_ipdown.add(change);
                    stock_kings_changelist.add(price);
                    //System.out.println("MAIN KINGS METHOD "+name+" "+symbol+" "+price+" "+change);
                }}
        } catch (IOException z) {
            //USE ALTERNATIVE SITE FOR STOCK KINGS
            try {
                sv = Jsoup.connect("http://www.dogsofthedow.com/largest-companies-by-market-cap.htm").userAgent("Opera").timeout(10 * 10000).get();
                Elements tbody =sv.select("tbody");
                Elements tbody2 =tbody.select("tbody");
                Elements tr =tbody2.select("tr");
                for (int i = 15; i <= 24; i++) {
                    if (stock_kings_namelist.size() < 20) {
                        Element td0 = tr.get(i).select("td").get(0);
                        Element td1 = tr.get(i).select("td").get(1);
                        Element td2 = tr.get(i).select("td").get(3);
                        Element td3 = tr.get(i).select("td").get(4);
                        String remove = td2.text().replace(",", "");
                        double add = Double.parseDouble(remove);
                        int value = (int) Math.round(add);
                        String added = String.valueOf(value);
                        if (add > 1000) {
                            added = added + " T";
                        } else {
                            added = added + " B";
                        }
                        try {
                            sv = Jsoup.connect("https://money.cnn.com/quote/quote.html?symb=" + td0.text()).timeout(10 * 10000).get();
                            Elements t = sv.select("tbody");
                            Elements tx = t.select("tr");
                            String td = tx.select("td").get(0).text();
                            String[] split = td.split(" ");
                            stock_kings_ipdown.add(split[0]);
                            //System.out.println("SPECIFIC QUOTE "+split[0]);

                        } catch (IOException d) {
                        }
                        stock_kings_namelist.add(td1.text());
                        stock_kings_symbollist.add(td0.text());
                        stock_kings_changelist.add(added);
                        //stock_kings_ipdown.add(td3.text());

//                    System.out.println(i-14+" "+td0.text()+" "+td1.text()+" "+added);
                    }
                }

            } catch (IOException e) {
                //Neither method works for Stock Kings..send an email to the developer
                e.printStackTrace();
            }    z.printStackTrace();
        }


    }
    public static void getMarketKingsCrypto(){
        try {
            crypto_data = Jsoup.connect("https://coinmarketcap.com/").timeout(10 * 10000).get();
            Elements table = crypto_data.select("tbody");
            Elements tr = table.select("tr");
            for (Element t :tr){
                String symbol_name = t.select("td").get(1).text();
                String[] split = symbol_name.split(" ", 2);
                String symbol = split[0].replace(" ", "");
                String name = split[1].replace(" ", "");
                String price = t.select("td").get(3).text();
                String change = t.select("td").get(6).text();
                String cap = t.select("td").get(2).text();
                DecimalFormat df = new DecimalFormat("0.00");
                df.setMaximumFractionDigits(2);
                crypto_kings_symbolist.add(symbol);
                double p = Double.parseDouble(price.replace("$", " ").replace(",", ""));
                price = df.format(p);
                crypto_kings_namelist.add(name);
                crypto_kings_pricelist.add(price);
                crypto_kings_changelist.add(change);
                double d = Double.parseDouble(cap.replace("$", " ").replace(",", ""));
                cap = df.format(d);
                int l = cap.length();
                long ti = 1000000000000L;
                if (l <= 12) {
                    cap = String.valueOf(d / 1000000);
                    crypto_kings_marketcaplist.add(cap.substring(0, 3) + " M");
                }
                if (l > 12) {
                    cap = String.valueOf(d / 1000000000);
                    crypto_kings_marketcaplist.add(cap.substring(0, 3) + " B");
                }
                if (l > 15) {
                    cap = String.valueOf(d / ti);
                    crypto_kings_marketcaplist.add(cap.substring(0, 3) + " T");
                }
                btc_market_cap_change = crypto_kings_changelist.get(0) + "";
                btc_market_cap_amount = (String) crypto_kings_pricelist.get(0);
            }
            // System.out.println("X "+symbol+" "+name+" "+price+" "+change);

        }catch (IOException n){
            //USING ALTERNATIVE SITE
            try {
                crypto_data = Jsoup.connect("https://finance.yahoo.com/cryptocurrencies").timeout(10 * 10000).get();
                Elements tbody =crypto_data.select("tbody");
                Elements tr =tbody.select("tr");
                for(int i=0;i<20;i++){
                    String symbol = tr.get(i).select("td").get(0).text();
                    String name = tr.get(i).select("td").get(1).text();
                    String price = tr.get(i).select("td").get(3).text().replace("+","");
                    String change = tr.get(i).select("td").get(4).text().replace("+","");
                    crypto_kings_symbolist.add(symbol);
                    crypto_kings_namelist.add(name);
                    crypto_kings_pricelist.add(price);
                    crypto_kings_changelist.add(change);
                    //System.out.println(symbol+" "+name+" "+price+" "+change);
                }
                btc_market_cap_change =crypto_kings_changelist.get(0)+"%";
                btc_market_cap_amount =(String) crypto_kings_pricelist.get(0);

            }catch (IOException o){
                //USING API AS LAST RESORT IF SITES ARE DOWN
                RequestQueue requestQueue = Volley.newRequestQueue(ApplicationContextProvider.getContext());
                final String url = "https://api.coinmarketcap.com/v2/ticker/?sort=rank";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("data");
                            JSONArray keys = obj.names ();
                            String market_cap = null;
                            String price =null;
                            for (int i = 0; i < 20; ++i) {
                                String key = keys.getString (i); // Here's your key
                                String value = obj.getString (key);// Here's your value
                                JSONObject jsonObject = new JSONObject(value);
                                String name = jsonObject.getString("name");
                                if (name.equalsIgnoreCase("XRP")){
                                    crypto_kings_namelist.add("Ripple");}else{
                                    crypto_kings_namelist.add(name);
                                }
                                String symbol = jsonObject.getString("symbol");
                                crypto_kings_symbolist.add(symbol);
                                JSONObject quotes =jsonObject.getJSONObject("quotes");
                                JSONArray ke = quotes.names ();
                                for(int a =0; a < ke.length(); ++a){
                                    String keyz = ke.getString (a); // Here's your key
                                    String valuez = quotes.getString (keyz);
                                    JSONObject jzO = new JSONObject(valuez);
                                    market_cap= jzO.getString("market_cap");
                                    price =jzO.getString("price");
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    df.setMaximumFractionDigits(2);
                                    String p =market_cap;
                                    String pr =price;
                                    double d = Double.parseDouble(p);
                                    double dr = Double.parseDouble(pr);
                                    p =df.format(d);
                                    pr =df.format(dr);
                                    int l =p.length();
                                    long t = 1000000000000L;
                                    crypto_kings_pricelist.add(pr);
                                    if (l<=12){
                                        p= String.valueOf(d/1000000);
                                        crypto_kings_marketcaplist.add(p.substring(0,3)+" M");}
                                    if (l>12){p= String.valueOf(d/1000000000);
                                        crypto_kings_marketcaplist.add(p.substring(0,3)+" B");}
                                    if (l>15){p= String.valueOf(d/t);
                                        crypto_kings_marketcaplist.add(p.substring(0,3)+" T");}
                                    String mc =jzO.getString("percent_change_24h");
                                    crypto_kings_changelist.add(mc);
                                }




                            }
                            btc_market_cap_amount =(String) crypto_kings_pricelist.get(0);
                            btc_market_cap_change =crypto_kings_changelist.get(0)+"";
                        } catch (JSONException e) {
                            //SEND A MESSAGE TO THE DEVELOPER AS ALL 3 METHODS HAVE FAILED
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //("An Error occured while making the request");
                        //SEND A MESSAGE TO THE DEVELOPER AS ALL 3 METHODS HAVE FAILED
                    }
                });
                requestQueue.add(jsonObjectRequest);


            }

        }
    }
    public static void getMarketKings(){
        getMarketKingsCrypto();
        getMarketKingsStock();
    }





    public static void toDouble(ArrayList array, String string){
        Double doub = Double.parseDouble(string);
        DecimalFormat DollarPlus = new DecimalFormat("#.##");
        DecimalFormat Between = new DecimalFormat("#.##");
        DecimalFormat Dimeless = new DecimalFormat(".####");

        if (doub>1){
            string=DollarPlus.format(doub);
        }
        if (doub<.1){
            string=Dimeless.format(doub);

        }
        else{
            //For values between .1 and 1
            string=Between.format(doub);

        }
        array.add("$ "+string);
    }

    public static void getCrypto_Data(){
        //Scrape data and if any Array is 0 use API as backup
        try {
            crypto_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").timeout(10 * 10000).get();
            Elements losers_table = crypto_data.getElementsByClass("table-responsive");
            Elements losers = crypto_data.select("div#losers-24h");
            Elements loser_symbol = losers.select("td.text-left");
            Elements loser_volume = losers.select("td.text-right");
            Elements loser_name_change = losers.select("td[data-sort]");
            for (Element x: loser_volume) {
                String v =x.getElementsByClass("volume").text().replace("$","").replace(",","");
                if (v.isEmpty()){}else{
                    if(Double.parseDouble(v)>250000){

                        for (Element s : loser_symbol) {
                            crypto_losers_symbollist.add(s.text());
                        }
                        Elements price = losers.select("a.price");
                        for(Element xx : price){
                            if(crypto_losers_pricelist.size()<20){
                                String url = xx.attr("data-usd");
                                toDouble(crypto_losers_pricelist,url);}
                        }
                        for (int i = 0; i < loser_name_change.size(); i++) {
                            if (i % 2 == 0) {
                                if(crypto_losers_namelist.size()<20){
                                    crypto_losers_namelist.add(loser_name_change.get(i).text());}
                            } else {
                                if(crypto_losers_changelist.size()<20){
                                    crypto_losers_changelist.add(loser_name_change.get(i).text());}
                            }
                        }
                    }}
            }
            Element winners_table = losers_table.get(2);
            Elements tbody = winners_table.select("tbody");
            Elements winners = crypto_data.select("div#gainers-24h");
            Elements winner_change = tbody.select("td[data-usd]");//Get's percentage change
            Elements winner_symbol = tbody.select("tr");
            Elements winner_name = tbody.select("img[src]");
            for (Element crypto_symbol : winner_symbol) {
                if(crypto_winners_symbollist.size()<20){
                    String symbol = crypto_symbol.select("td.text-left").text();
                    crypto_winners_symbollist.add(symbol);
                    typelist.add("Cryptocurrency");}
            }
            Elements link = winners.select("a.price");
            for(Element x : link){
                if(crypto_winners_pricelist.size()<20){
                    String url = x.attr("data-usd");
                    toDouble(crypto_winners_pricelist,url);}
            }
            for (Element crypto_name : winner_name) {
                if(crypto_winners_namelist.size()<20){
                    String name = crypto_name.attr("alt");
                    crypto_winners_namelist.add(name);}
            }
            for (Element crypto_change : winner_change) {
                if(crypto_winners_changelist.size()<20){
                    crypto_winners_changelist.add(crypto_change.text());}
            }
        } catch (IOException e) {
            //Use alternative method
            try{
                crypto_data = Jsoup.connect("https://www.coingecko.com/en/coins/trending").timeout(10 * 10000).get();
                Elements tbody = crypto_data.select("tbody");
                Elements winner_change = tbody.select("tr");
                for(Element x :winner_change){
                    String symbol = x.select("span").get(0).text();
                    String name = x.select("span").get(1).text();
                    String price = x.select("span").get(4).text().replace("$","");
                    String change = x.select("span").get(5).text();
                    if(crypto_winners_namelist.size()<20){
                        crypto_winners_symbollist.add(symbol);
                        crypto_winners_namelist.add(name);
                        crypto_winners_changelist.add(change);
                        typelist.add("Cryptocurrency");
                        toDouble(crypto_winners_pricelist,price);
                        //System.out.println("WINNERS "+name+" "+symbol+" "+price+" "+change);
                    }

                }
            }
            catch (IOException i){
                //i.printStackTrace();
                //USE 3rd OPTION api IF NECCESSARY as winners and losers methods have failed
                //Send a message to me stating how all 3 methods have failed.

            }}
        //GETTING CRYPTO MARKET CAP LEADERS
        try {
            crypto_data = Jsoup.connect("https://coinmarketcap.com/").timeout(10 * 10000).get();
            Elements table = crypto_data.select("tbody");
            Elements tr = table.select("tr");
            for (Element t :tr){
                if(crypto_kings_marketcaplist.size()<20) {
                    String symbol_name = t.select("td").get(1).text();
                    String[] split = symbol_name.split(" ", 2);
                    String symbol = split[0].replace(" ", "");
                    String name = split[1].replace(" ", "");
                    String price = t.select("td").get(3).text();
                    String change = t.select("td").get(6).text();
                    String cap = t.select("td").get(2).text();
                    DecimalFormat df = new DecimalFormat("0.00");
                    df.setMaximumFractionDigits(2);
                    crypto_kings_symbolist.add(symbol);
                    double p = Double.parseDouble(price.replace("$", " ").replace(",", ""));
                    price = df.format(p);
                    crypto_kings_namelist.add(name);
                    crypto_kings_pricelist.add(price);
                    crypto_kings_changelist.add(change);
                    double d = Double.parseDouble(cap.replace("$", " ").replace(",", ""));
                    cap = df.format(d);
                    int l = cap.length();
                    long ti = 1000000000000L;
                    if (l <= 12) {
                        cap = String.valueOf(d / 1000000);
                        crypto_kings_marketcaplist.add(cap.substring(0, 3) + " M");
                    }
                    if (l > 12) {
                        cap = String.valueOf(d / 1000000000);
                        crypto_kings_marketcaplist.add(cap.substring(0, 3) + " B");
                    }
                    if (l > 15) {
                        cap = String.valueOf(d / ti);
                        crypto_kings_marketcaplist.add(cap.substring(0, 3) + " T");
                    }
                    btc_market_cap_change = crypto_kings_changelist.get(0) + "";
                    btc_market_cap_amount = (String) crypto_kings_pricelist.get(0);
                }
            }
        }catch (IOException n){
            //USING ALTERNATIVE SITE
            try {
                crypto_data = Jsoup.connect("https://finance.yahoo.com/cryptocurrencies").timeout(10 * 10000).get();
                Elements tbody =crypto_data.select("tbody");
                Elements tr =tbody.select("tr");
                for(int i=0;i<20;i++){
                    String symbol = tr.get(i).select("td").get(0).text();
                    String name = tr.get(i).select("td").get(1).text();
                    String price = tr.get(i).select("td").get(3).text().replace("+","");
                    String change = tr.get(i).select("td").get(4).text().replace("+","");
                    crypto_kings_symbolist.add(symbol);
                    crypto_kings_namelist.add(name);
                    crypto_kings_pricelist.add(price);
                    crypto_kings_changelist.add(change);
                }
                btc_market_cap_change =crypto_kings_changelist.get(0)+"%";
                btc_market_cap_amount =(String) crypto_kings_pricelist.get(0);

            }catch (IOException o){
                //USING API AS LAST RESORT IF SITES ARE DOWN
                RequestQueue requestQueue = Volley.newRequestQueue(ApplicationContextProvider.getContext());
                final String url = "https://api.coinmarketcap.com/v2/ticker/?sort=rank";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("data");
                            JSONArray keys = obj.names ();
                            String market_cap = null;
                            String price =null;
                            for (int i = 0; i < 20; ++i) {
                                String key = keys.getString (i); // Here's your key
                                String value = obj.getString (key);// Here's your value
                                JSONObject jsonObject = new JSONObject(value);
                                String name = jsonObject.getString("name");
                                if (name.equalsIgnoreCase("XRP")){
                                    crypto_kings_namelist.add("Ripple");}else{
                                    crypto_kings_namelist.add(name);
                                }
                                String symbol = jsonObject.getString("symbol");
                                crypto_kings_symbolist.add(symbol);
                                JSONObject quotes =jsonObject.getJSONObject("quotes");
                                JSONArray ke = quotes.names ();
                                for(int a =0; a < ke.length(); ++a){
                                    String keyz = ke.getString (a); // Here's your key
                                    String valuez = quotes.getString (keyz);
                                    JSONObject jzO = new JSONObject(valuez);
                                    market_cap= jzO.getString("market_cap");
                                    price =jzO.getString("price");
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    df.setMaximumFractionDigits(2);
                                    String p =market_cap;
                                    String pr =price;
                                    double d = Double.parseDouble(p);
                                    double dr = Double.parseDouble(pr);
                                    p =df.format(d);
                                    pr =df.format(dr);
                                    int l =p.length();
                                    long t = 1000000000000L;
                                    crypto_kings_pricelist.add(pr);
                                    if (l<=12){
                                        p= String.valueOf(d/1000000);
                                        crypto_kings_marketcaplist.add(p.substring(0,3)+" M");}
                                    if (l>12){p= String.valueOf(d/1000000000);
                                        crypto_kings_marketcaplist.add(p.substring(0,3)+" B");}
                                    if (l>15){p= String.valueOf(d/t);
                                        crypto_kings_marketcaplist.add(p.substring(0,3)+" T");}
                                    String mc =jzO.getString("percent_change_24h");
                                    crypto_kings_changelist.add(mc);
                                }




                            }
                            btc_market_cap_amount =(String) crypto_kings_pricelist.get(0);
                            btc_market_cap_amount =btc_market_cap_amount.replace(" ","");
                            btc_market_cap_change =crypto_kings_changelist.get(0)+"";
                        } catch (JSONException e) {
                            //SEND A MESSAGE TO THE DEVELOPER AS ALL 3 METHODS HAVE FAILED
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //("An Error occured while making the request");
                        //SEND A MESSAGE TO THE DEVELOPER AS ALL 3 METHODS HAVE FAILED
                    }
                });
                requestQueue.add(jsonObjectRequest);


            }

        }
    }

    public static void getStock_Data() {
        //Getting winners and losers
        sv = null;
        try {
//WINNERS
            sv = Jsoup.connect("https://finance.yahoo.com/gainers").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbody =sv.select("tbody");
            Elements tr =tbody.select("tr");
            for(int i=0;i<20;i++){
                String symbol = tr.get(i).select("td").get(0).text();
                String name = tr.get(i).select("td").get(1).text();
                String price = tr.get(i).select("td").get(2).text().replace("+","");
                String change = tr.get(i).select("td").get(4).text().replace("+","");
                stock_winners_symbollist.add(symbol);
                stock_winners_namelist.add(name);
                stock_winners_pricelist.add(price);
                stock_winners_changelist.add(change);
                typelist.add("Stock");
                //System.out.println("FIRST METHOD WINNERS"+symbol+" "+name+" "+price+" "+change);

            }
            Document sv1=null;
            sv1 = Jsoup.connect("https://finance.yahoo.com/losers").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbodyl = sv1.select("tbody");
            Elements trl = tbodyl.select("tr");
            for (int il = 0; il < 20; il++) {
                String lsymbol = trl.get(il).select("td").get(0).text();
                String lname = trl.get(il).select("td").get(1).text();
                String lprice = trl.get(il).select("td").get(2).text().replace("+", "");
                String lchange = trl.get(il).select("td").get(4).text().replace("+", "");
                stock_losers_symbollist.add(lsymbol);
                stock_losers_namelist.add(lname);
                stock_losers_pricelist.add(lprice);
                stock_losers_changelist.add(lchange);
                typelist.add("Stock");

            }

        } catch (IOException e) {
//ALTERNATIVE METHOD
            getStock_DataBACKUP();
            e.printStackTrace();
        }

        try {
//GET STOCK KINGS
            sv = Jsoup.connect("https://www.tradingview.com/markets/stocks-usa/market-movers-large-cap/").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbody =sv.select("tbody");

            Elements tr =tbody.select("tr");
            for (Element z : tr) {
                if (stock_kings_symbollist.size() < 20) {


                    String td0 = z.select("td").get(0).text();
                    String[] split = td0.split(" ");
                    String symbol = split[0];
                    String name = td0.replace(split[0],"");
                    String td1 = z.select("td").get(1).text();
                    String price = td1;
                    String td2 = z.select("td").get(2).text();
                    String change = td2;
                    stock_kings_symbollist.add(symbol);
                    stock_kings_namelist.add(name);
                    stock_kings_ipdown.add(change);
                    stock_kings_changelist.add(price);
                    //System.out.println("MAIN KINGS METHOD "+name+" "+symbol+" "+price+" "+change);
                }}
        } catch (IOException z) {
            //USE ALTERNATIVE SITE FOR STOCK KINGS
            try {
                sv = Jsoup.connect("http://www.dogsofthedow.com/largest-companies-by-market-cap.htm").userAgent("Opera").timeout(10 * 10000).get();
                Elements tbody =sv.select("tbody");
                Elements tbody2 =tbody.select("tbody");
                Elements tr =tbody2.select("tr");
                for (int i = 15; i <= 24; i++) {
                    if (stock_kings_namelist.size() < 20) {
                        Element td0 = tr.get(i).select("td").get(0);
                        Element td1 = tr.get(i).select("td").get(1);
                        Element td2 = tr.get(i).select("td").get(3);
                        Element td3 = tr.get(i).select("td").get(4);
                        String remove = td2.text().replace(",", "");
                        double add = Double.parseDouble(remove);
                        int value = (int) Math.round(add);
                        String added = String.valueOf(value);
                        if (add > 1000) {
                            added = added + " T";
                        } else {
                            added = added + " B";
                        }
                        try {
                            sv = Jsoup.connect("https://money.cnn.com/quote/quote.html?symb=" + td0.text()).timeout(10 * 10000).get();
                            Elements t = sv.select("tbody");
                            Elements tx = t.select("tr");
                            String td = tx.select("td").get(0).text();
                            String[] split = td.split(" ");
                            stock_kings_ipdown.add(split[0]);
                            //System.out.println("SPECIFIC QUOTE "+split[0]);

                        } catch (IOException d) {
                        }
                        stock_kings_namelist.add(td1.text());
                        stock_kings_symbollist.add(td0.text());
                        stock_kings_changelist.add(added);
                        //stock_kings_ipdown.add(td3.text());

//                    System.out.println(i-14+" "+td0.text()+" "+td1.text()+" "+added);
                    }
                }

            } catch (IOException e) {
                //Neither method works for Stock Kings..send an email to the developer
                e.printStackTrace();
            }    z.printStackTrace();
        }


    }

    public static void getStock_DataBACKUP(){
        try {
            sv = Jsoup.connect("http://money.cnn.com/data/hotstocks/").userAgent("Opera").timeout(10 * 10000).get();
            Elements ddd = sv.getElementsByClass("wsod_dataTable wsod_dataTableBigAlt");
            Element ff = ddd.get(1);
            Elements a = ff.select("a");
            Elements b = ff.select("span[title]");
            Elements aa = ff.select("span.posData");
            Elements bb = ff.select("span[stream]");

            if(stock_winners_symbollist.size()<20){
            for (Element stock_symbol : a) {
                String symbol = stock_symbol.select("a.wsod_symbol").text();
                stock_winners_symbollist.add(symbol);
                typelist.add("Stock");}
            for (Element stock_name : b) {
                String name = stock_name.text();
                if (name.isEmpty()) {
                } else {
                    stock_winners_namelist.add(name); } }
            for (Element stock_price : bb) {
                String price = stock_price.text();
                if (price.isEmpty()) {
                } else {
                    if (price.contains("+")||price.contains("-")||price.contains("%")) {
                    }else{
                        stock_winners_pricelist.add(price);} } }
            for (Element stock_change : aa) {
                String change = stock_change.text();
                if (change.isEmpty()) {
                } else {
                    if (change.contains("%")) {
                        stock_winners_changelist.add(change); } } }
            //STOCK LOSERS ARRAYS
            Element fl = ddd.get(2);
            Elements al = fl.select("a");
            Elements bl = fl.select("span[title]");
            Elements aal = fl.select("span.negData");
            Elements bb1 = fl.select("span[stream]");
                Constructor_Stock_Equities stock_equities = new Constructor_Stock_Equities();
                for (Element x : al) {
                stock_equities.setStock_losers_symbol(x.text()); }
            for (Element x : bl) {
                stock_equities.setStock_losers_name(x.text()); }
            for (Element stock_price : bb1) {
                String price = stock_price.text();
                if (price.isEmpty()) {
                } else {
                    if (price.contains("+")||price.contains("-")||price.contains("%")) {
                    }else{
                        stock_equities.setStock_losers_price(price);} } }
            for (int i = 0; i < aal.size(); i++) {
                if (i % 2 == 0) {
                    stock_equities.setStock_losers_percent_change(aal.get(i).text()); } }
          stock_losers_feedItems.add(stock_equities);
        }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void ProcessXml(org.w3c.dom.Document data) {
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
                                it.setThumbnailUrl(m.group(1));
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

                    all_feedItems.add(it);


                }
            }
        }
    }

    public static org.w3c.dom.Document GoogleRSWorldMarketsFeed() {
        try {
            URL url;
            String address = "https://news.google.com/news/rss/search/section/q/" + "World%20Financial%20Markets" + "?ned=us&gl=US&hl=en";
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




    public static void get_icos(){
        Document e =null;
        Constructor_Icos it =new Constructor_Icos();
        try {
            e = Jsoup.connect("https://topicolist.com/ongoing-icos/").userAgent("Mozilla").timeout(10 * 100000).get();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Elements tb =e.select("tbody");
        Elements ref =tb.select("tr");
        for(int i =0;i<ref.size();i++) {
            String w = ref.get(i).select("a").attr("title");
            String x = ref.get(i).select("td").get(2).text();
            String y = ref.get(i).select("td").get(3).text();
            String z = ref.get(i).select("td").get(4).text();
            DateFormat f = new SimpleDateFormat("MMMM dd, yyyy");
            Date formatted = null;
            Date formatted2 = null;
            try {
                if(y.isEmpty()){formatted = f.parse("December 31, 2099");}else{
                    formatted = f.parse(y);}
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            try {
                if(z.isEmpty()){formatted2 = f.parse("December 31, 2099");}else{
                    formatted2 = f.parse(z);}
            } catch (ParseException e1) {
                formatted2 = formatted;
                e1.printStackTrace();
            }

            ico_name.add(w);
            ico_message.add(x);
            String out = formatted.toString().substring(4, 10);
            String put = formatted.toString().substring(24,28);
            String out2 = formatted2.toString().substring(4, 10);
            String put2 = formatted2.toString().substring(24,28);
            String output=out+" "+put;
            String output2=out2+" "+put2;
            ico_startdate.add(output);
            ico_enddate.add(output2);
        }
        for (int i=0;i<ico_name.size();i++) {
            //("SIZE "+ico_name.get(i)+" "+ico_message.get(i)+" "+ico_startdate.get(i)+" "+ico_enddate.get(i));
        }
    }

    public static void get_ipos(){
        Document d =null;
        try {
            d= Jsoup.connect("https://www.marketbeat.com/IPOs").userAgent("Mozilla").timeout(10 * 100000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements z=d.select("tbody");
        Elements tr = z.select("tr");
        for (int i = 0; i < tr.size(); i++) {
            Element row = tr.get(i);
            Elements cols = row.select("td");
            ipo_date.add(cols.get(0).text());
            ipo_name.add(cols.get(1).text());
            ipo_range.add(cols.get(2).text());
            ipo_volume.add(cols.get(3).text());
        }

    }

    public static void getWorldMarkets(){
        dow_change = null;
        dow_amount = null;
        //This method can be improved by iterating td and using arrays of strings instead of individual strings.
        Document us=null;
        try{
            us =Jsoup.connect("https://money.cnn.com/data/world_markets/americas/").timeout(10 *10000).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        Element etable1 = us.getElementById("wsod_indexDataTableGrid");
        Elements et1 =etable1.select("tr");
        Elements amer = et1.select("td");
        dow_name=amer.select("td").get(1).text();
        dow_change=amer.select("td").get(4).text();
        dow_amount=amer.select("td").get(5).text();

        sp_name=amer.select("td").get(8).text();
        sp_change=amer.select("td").get(11).text();
        sp_amount=amer.select("td").get(12).text();

        bov_name =amer.select("td").get(15).text();
        bov_change =amer.select("td").get(18).text();
        bov_amount =amer.select("td").get(19).text();


        Document euro=null;
        try{
            euro =Jsoup.connect("https://money.cnn.com/data/world_markets/europe/").timeout(10 *10000).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        Element etable = euro.getElementById("wsod_indexDataTableGrid");
        Elements et =etable.select("tr");
        Elements edt = et.select("td");
        ftse_name=edt.select("td").get(1).text();
        ftse_change=edt.select("td").get(4).text();
        ftse_amount=edt.select("td").get(5).text();

        cac_name=edt.select("td").get(15).text();
        cac_change=edt.select("td").get(18).text();
        cac_amount=edt.select("td").get(19).text();

        dax_name=edt.select("td").get(22).text();
        dax_change=edt.select("td").get(25).text();
        dax_amount=edt.select("td").get(26).text();


        Document asia=null;
        try{
            asia =Jsoup.connect("https://money.cnn.com/data/world_markets/asia/").timeout(10 *10000).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        Element table = asia.getElementById("wsod_indexDataTableGrid");
        Elements t =table.select("tr");
        Elements dt = t.select("td");
        shse_name=dt.select("td").get(8).text();
        shse_change=dt.select("td").get(11).text();
        shse_amount=dt.select("td").get(12).text();

        hang_name=dt.select("td").get(15).text();
        hang_change=dt.select("td").get(18).text();
        hang_amount=dt.select("td").get(19).text();

        nikk_name=dt.select("td").get(29).text();
        nikk_change=dt.select("td").get(32).text();
        nikk_amount=dt.select("td").get(33).text();

    }

    public static void clearMainData(){
        stock_kings_symbollist.clear();
        stock_kings_namelist.clear();
          stock_kings_ipdown.clear();
          stock_kings_changelist.clear();
        stock_winners_symbollist.clear();
        stock_winners_namelist.clear();
        stock_winners_changelist.clear();
          stock_losers_symbollist.clear();
          stock_losers_namelist.clear();
          stock_losers_changelist.clear();
          crypto_winners_changelist.clear();
          crypto_winners_namelist.clear();
          crypto_winners_symbollist.clear();
          crypto_losers_changelist.clear();
          crypto_losers_namelist.clear();
          crypto_losers_symbollist.clear();
          crypto_kings_symbolist.clear();
          crypto_kings_namelist.clear();
          crypto_kings_marketcaplist.clear();
          crypto_kings_changelist.clear();
    }

    public static void clearWinnersData(){
        if(stock_winners_symbollist.size()>19){
        stock_winners_symbollist.clear();
        stock_winners_namelist.clear();
        stock_winners_changelist.clear();
        stock_winners_pricelist.clear();
        crypto_winners_changelist.clear();
        crypto_winners_pricelist.clear();
        crypto_winners_namelist.clear();
        crypto_winners_symbollist.clear();
        }}

    public static void clearLosersData(){
        if(stock_losers_symbollist.size()>20){
        stock_losers_symbollist.clear();
        stock_losers_namelist.clear();
        stock_losers_changelist.clear();
        stock_losers_pricelist.clear();
        crypto_losers_pricelist.clear();
        crypto_losers_changelist.clear();
        crypto_losers_namelist.clear();
        crypto_losers_symbollist.clear();}}

    public static void clearKingsData(){
        if(crypto_kings_symbolist.size()>20){
        stock_kings_symbollist.clear();
        stock_kings_namelist.clear();
        stock_kings_ipdown.clear();
        stock_kings_changelist.clear();
        crypto_kings_symbolist.clear();
        crypto_kings_namelist.clear();
        crypto_kings_pricelist.clear();
        crypto_kings_changelist.clear();}}

     public static void clearMasterNodesData(){
        masternode_feedItems.clear();
        masternode_marketcap.clear();
        masternode_name.clear();
        masternode_marketcap.clear();
        masternode_node_count.clear();
        masternode_percent_change.clear();
        masternode_symbol.clear();
        masternode_purchase_value.clear();
     }
}


