package equities.com.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static equities.com.myapplication.Activity_Markets_Main.ap_info;
import static equities.com.myapplication.Constructor_App_Variables._AllDays;
import static equities.com.myapplication.Constructor_App_Variables.aequity_exchanges;
import static equities.com.myapplication.Constructor_App_Variables.current_percentage_change;
import static equities.com.myapplication.Constructor_App_Variables.current_updated_price;
import static equities.com.myapplication.Constructor_App_Variables.graph_date;
import static equities.com.myapplication.Constructor_App_Variables.graph_high;
import static equities.com.myapplication.Constructor_App_Variables.graph_low;
import static equities.com.myapplication.Constructor_App_Variables.graph_market_cap;
import static equities.com.myapplication.Constructor_App_Variables.graph_volume;

public class Test_Methods {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        Constructor_App_Variables app_info =new Constructor_App_Variables();
        app_info.setMarketType("Crypto");
        app_info.setMarketName("Ethereum");
        app_info.setMarketSymbol("eth");
getTopNewsStories();
        //get_crypto_info();
    }
    public static void updaqte(){
        Constructor_App_Variables app_info =new Constructor_App_Variables();
        String name="gsat";
         Document caps= null;
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
            System.out.println(td);
        }catch (IOException e){
            System.out.println(e);

            //NEED A BACKUP for stockupdate
        }}
    public static void updateFinancialData(){
        Constructor_App_Variables app_info =new Constructor_App_Variables();
        Document caps = null;
        if(app_info.getMarketType()=="Crypto"||app_info.getMarketType()=="Cryptocurrency"){
            if(ap_info.getMarketName().equalsIgnoreCase("XRP")){
                ap_info.setMarketName("Ripple");
            }
            try {
                caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + ap_info.getMarketName()).userAgent("Opera").timeout(10 * 10000).get();
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
                try{
                caps = Jsoup.connect("https://www.coingecko.com/en/coins/" + ap_info.getMarketName().toLowerCase()).userAgent("Opera").timeout(10 * 10000).get();
                    Elements qu =caps.getElementsByClass("mt-3");
                    Element z = qu.select("span").get(0);
                    Element z1 = qu.select("span").get(1);
                    Element z2 = qu.select("span").get(152);
                    current_percentage_change.clear();
                    current_updated_price.clear();
                    current_percentage_change.add(z1.text());
                    current_updated_price.add(z.text());
                    app_info.setCurrent_volume(z2.text());
            } catch (IOException i) {
                i.printStackTrace();}
            }
        }else{
            try {
                caps = Jsoup.connect("https://money.cnn.com/quote/quote.html?symb="+app_info.getMarketSymbol()).userAgent("Opera").timeout(10 * 10000).get();
                Element tb= caps.select("tbody").get(1);
                String td = tb.select("td").get(7).text();
                Element tb1= caps.select("tbody").get(0);
                String td0 = String.valueOf(tb1.select("td").get(0).select("span").text());
                String td1 = String.valueOf(tb1.select("td").get(1).select("span").get(4).text());
                current_percentage_change.clear();
                current_updated_price.clear();
                current_percentage_change.add(td1);
                String [] split = td0.split(" ");
                current_updated_price.add(split[0]);
                app_info.setCurrent_volume(td);

            }catch (IOException e){
                try{
                    caps = Jsoup.connect("https://finance.yahoo.com/quote/" + ap_info.getMarketSymbol()).userAgent("Opera").timeout(10 * 10000).get();
                    current_percentage_change.clear();
                    current_updated_price.clear();
                    Elements ez =caps.select("td[data-test]");
                    String qu =caps.select("div[data-reactid]").get(50).text();
                    String [] split = qu.split(" ");
                    current_updated_price.add(split[0]);
                    current_percentage_change.add(split[2].replace("(","").replace(")",""));
                    ap_info.setMarketCap(ez.get(8).text());
                    ap_info.setCurrent_volume(ez.get(6).text());
                } catch (IOException i) {
                    current_percentage_change.clear();
                    current_updated_price.clear();
                    current_percentage_change.add("Updating");
                    current_updated_price.add("Updating");
                    app_info.setCurrent_volume("Updating");}

                }

        }
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
            System.out.println("CRYPTO POINTS TIME IS " + duration / 1000000000 + " seconds");
        } catch (IOException e) {
            try{
                get_crypto_info();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }





    }
    public static void genTest(){
        Document caps = null;
        try {

            //            //System.out.println("Trying cnn for"+ app_info.getMarketSymbol());
            caps = Jsoup.connect("https://money.cnn.com/quote/quote.html?symb=ctl").userAgent("Opera").timeout(10 * 10000).get();
            Element tb= caps.select("tbody").get(1);
            String td = tb.select("td").get(7).text();
            Element tb1= caps.select("tbody").get(0);
            String td0 = String.valueOf(tb1.select("td").get(0).select("span").text());
            String td1 = String.valueOf(tb1.select("td").get(1).select("span").get(4).text());
            current_percentage_change.clear();
            current_updated_price.clear();
            current_percentage_change.add(td1);
            String [] split = td0.split(" ");
            current_updated_price.add(split[0]);
            //app_info.setCurrent_volume(td);
            System.out.println("Trying cnn for"+ td);
        }catch (Exception e){
            try {
                caps = Jsoup.connect("https://finance.yahoo.com/quote/ctl").userAgent("Opera").timeout(10 * 10000).get();
                current_percentage_change.clear();
                current_updated_price.clear();
                Elements ez =caps.select("td[data-test]");
                String qu =caps.select("div[data-reactid]").get(50).text();
                String [] split = qu.split(" ");
                current_updated_price.add(split[0]);
                current_percentage_change.add(split[2].replace("(","").replace(")",""));
                ap_info.setMarketCap(ez.get(8).text());
                ap_info.setCurrent_volume(ez.get(6).text());
                System.out.println("Trying yahoo for"+ ez.get(6).text());

            }catch (IOException i){}
        }
    }
    public static void getTopNewsStories(){
        String[] main_page_stock_news_urls = new String[]{"https://www.msn.com/en-us/money","https://money.cnn.com/data/markets/","https://finance.yahoo.com/","https://www.google.com/finance","https://www.bloomberg.com/"};
        String[] main_page_crypto_news_urls = new String[]{"https://www.coindesk.com","https://cryptonews.com"};

        Double a =1.00;
        //Double.parseDouble(ap_info.getBitcoinPrice());
        Double b =2.00;
        //Double.parseDouble(ap_info.getNasdaqPrice());
        Random random = new Random();
        int stock = random.nextInt( main_page_stock_news_urls. length);
        int crypto = random.nextInt( main_page_crypto_news_urls. length);
        if (a>b){
            Document doc = null;
            try {
                doc = Jsoup.connect(main_page_stock_news_urls[stock]).timeout(10 * 1000).get();
                if(stock==0){}
                if(stock==1){}
                if(stock==2){}
                if(stock==3){}
                if(stock==4){}



            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(doc);
            //Log.println(Log.INFO,"TAG", String.valueOf(doc));

        }else{
            Document doc = null;
            try {
                crypto=2;
                doc = Jsoup.connect(main_page_crypto_news_urls[crypto]).timeout(10 * 1000).get();
                if(crypto==0){
                    Element f =doc.getElementById("featured-articles");
                    Element link = f.select("a").first();
                    Element image = link.select("img").first();
                    String url = image.absUrl("src");
                    System.out.println("LINK TITLE " + link.attr("title"));
                    System.out.println("LINK URL " + link.attr("href"));
                    System.out.println("LINK IMAGE URL " + url);

                }
                if(crypto==1){
                    Elements z = doc.getElementsByClass("cn-tile article");
                    Element link = z.select("a").first();
                    Element image = link.select("img").first();
                    String url = image.absUrl("src");
                    Element i = doc.getElementsByClass("props").get(0);
                    String alink = i.select("a").get(1).text();

                    System.out.println("LINK IMAGE URL " + url);
                    System.out.println("LINK URL " + main_page_crypto_news_urls[crypto]+link.attr("href"));
                    System.out.println("LINK TITLE " + alink);

                }

                if(crypto==2){
                    Elements e =doc.select("div.fsp");
                    Element link = e.select("a").first();
//                    Element image = link.select("amp-img").first();
//                      String url = image.absUrl("src");
                  //  System.out.println("LINK TITLE " + link.attr("title"));
                    //System.out.println("LINK URL " + link.attr("href"));
  //                  System.out.println("LINK IMAGE URL " + url);

                    System.out.println("hello "+doc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Log.println(Log.INFO,"TAG", String.valueOf(doc));
        }


    }

}
