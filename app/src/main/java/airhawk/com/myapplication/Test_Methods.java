package airhawk.com.myapplication;

import android.content.Context;
import android.text.Html;
import android.util.Log;


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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static airhawk.com.myapplication.Activity_Main.aequity_name_arraylist;
import static airhawk.com.myapplication.Activity_Main.aequity_symbol_arraylist;
import static airhawk.com.myapplication.Constructor_App_Variables.*;
import static airhawk.com.myapplication.Service_Main_Equities.stock_kings_changelist;
import static airhawk.com.myapplication.Service_Main_Equities.stock_kings_namelist;
import static airhawk.com.myapplication.Service_Main_Equities.stock_kings_symbollist;
import static com.android.volley.VolleyLog.TAG;

public class Test_Methods {
    static ArrayList exchange_url = new ArrayList<>();
    static ArrayList exchange_name = new ArrayList<>();
    private static Context mContext;
    static String cryptopia_list;

    public static void main(String[] args) {
       // get_masternodes();
       // get_icos();
       // get_ipos();
       // find_urls();
       // get_ipos();
       // getWorldMarkets();
//get_stock_points();
        getSavedEquities();
    }
    public static void getWorldMarkets(){
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







    public static void getStocks_Market_Caps() {
        Document z =null;
        try {
            z = Jsoup.connect("https://finance.yahoo.com").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table = z.getElementById("Lead-2-FinanceHeader-Proxy");
        Elements as = table.select("a[title]");
        Elements as6 = table.select("li");
        sp_name = as.get(0).text().replace("&", "");
        dow_name = as.get(2).text();
        nas_name = as.get(4).text();
        Elements as1 = z.select("h3>span");
        sp_amount =as1.get(0).text();
        dow_amount=as1.get(1).text();
        nas_amount=as1.get(2).text();
        Elements as2 =z.select("span>span");
        sp_change =as2.get(2).text();
        dow_change=as2.get(3).text();
        nas_change=as2.get(4).text();
        if (sp_change.contains("-")){
            sp_flow=true;
        }
        if (dow_change.contains("-")){
            dow_flow=true;
        }
        if (nas_change.contains("-")){
            nd_flow=true;
        }
        System.out.println(sp_name+" "+sp_amount+" "+sp_change);
        System.out.println(dow_name+" "+dow_amount+" "+dow_change);
        System.out.println(nas_name+" "+nas_amount+" "+nas_change);
        System.out.println(as6.text());
    }

    public static void getSavedEquities(){

        ArrayList b =new ArrayList();
        b.add("GSAT");
        b.add("DPW");
        b.add("APPL");
        ArrayList c =new ArrayList();
        c.add("Stock");
        c.add("Stock");
        c.add("Stock");
                for( int x=0;x<b.size();x++) {
            Document cap = null;
            if (c.get(x).equals("Stock")) {

                try {
                    cap = Jsoup.connect("https://finance.yahoo.com/quote/" + b.get(x)).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Element test = cap.select("div[data-reactid='34']").first().select("span").get(1);
                String foofoo = test.text().toString().replace("(", "").replace(")", "");
                String[] foo = foofoo.split(" ");
                String f = foo[1];
                System.out.println("MAYBE? "+cap.getElementsByClass("D(ib) Mend(20px)").text());
                System.out.println("THIS IS F? "+ f);
                current_percentage_change.add(f);
                System.out.println("getSavedEquities "+ current_percentage_change.get(x));
            }

        }
    }

    public static void get_stock_points2() {
        //("Get stock points called");
        String marname = "aapl";
        Document d = null;
        try {
            d = Jsoup.connect("https://finance.yahoo.com/quote/"+marname+"/history?period1=345456000&period2=1542441600&interval=1d&filter=history&frequency=1d").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

            Elements x = d.getElementsByAttributeValue("class","BdT Bdc($c-fuji-grey-c) Ta(end) Fz(s) Whs(nw)");


            for(int z =0; z < x.size();z++){
                String text =x.get(z).text();
                if(text.contains("Dividend")){continue;}else{
                String date =x.get(z).select("td").get(0).text();
                graph_date.add(date);
                String close =x.get(z).select("td").get(5).text();
                graph_high.add(close);
                String volume =x.get(z).select("td").get(6).text();
                graph_volume.add(volume);

                //(date+" "+close+" "+volume);
                List<String> numbers = graph_high;
                //Collections.reverse(numbers);
                _AllDays = numbers;}}
        //(graph_high.size());
        }

    public static void getStock_Kings() {
        int x;
        Document sv = null;
        String t;
        try {
            sv = Jsoup.connect("http://fortune.com/global500/list/filtered?sortBy=profits&first500").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements e = sv.select("li");
        for (int i = 0; i < e.size(); i++) {
            Elements z = e.select("span");
            for (x = 1; x < e.size(); x = x + 3) {
                stock_kings_namelist.add(z.get(x).text());
                //(z.get(x).text());
                t = z.get(x).text();
                for (int s = 0; s < aequity_name_arraylist.size(); s++) {
                    if (aequity_name_arraylist.contains(t)) {
                        stock_kings_symbollist.add(aequity_symbol_arraylist.get(s));
                        //("Yo " + aequity_symbol_arraylist.get(s));
                    }
                }
                //    aequity_name_arraylist;
                //ap_info.setMarketSymbol();
                //stock_kings_changelist.add();
            }


            for (int n = 2; n < e.size(); n = n + 3) {
                ////(z.get(n).text().substring(1, 5).replace(",", ".") + " B");
                stock_kings_changelist.add(z.get(n).text().substring(1, 5).replace(",", ".") + " B");
            }
        }

    }

    public static void get_crypto_listings() {

        exchange_url.add("https://www.Binance.com");
        exchange_url.add("https://www.OKEx.com");
        exchange_url.add("https://www.Huobi.com");
        exchange_url.add("https://www.Bithumb.com");
        exchange_url.add("https://www.ZB.com");
        exchange_url.add("https://www.Bitfinex.com");
        exchange_url.add("https://www.HitBTC.com");
        exchange_url.add("https://www.Bit-Z.com");
        exchange_url.add("https://www.Bibox.com");
        exchange_url.add("https://www.Coinsuper.com");
        exchange_url.add("https://www.BCEX.com");
        exchange_url.add("https://www.LBank.com");
        exchange_url.add("https://www.DigiFinex.com");
        exchange_url.add("https://www.Upbit.com");
        exchange_url.add("https://pro.Coinbase.com");
        exchange_url.add("https://www.Simex.com");
        exchange_url.add("https://www.OEX.com");
        exchange_url.add("https://www.Kraken.com");
        exchange_url.add("https://www.Cryptonex.com");
        exchange_url.add("https://www.BitMart.com");
        exchange_url.add("https://www.Allcoin.com");
        exchange_url.add("https://www.IDAX.com");
        exchange_url.add("https://www.RightBTC.com");
        exchange_url.add("https://www.IDCM.com");
        exchange_url.add("https://www.YoBit.com");
        exchange_url.add("https://www.DragonEX.io");
        exchange_url.add("https://www.Gate.io");
        exchange_url.add("https://www.Fatbtc.com");
        exchange_url.add("https://www.Exrates.com");
        exchange_url.add("https://www.CoinsBank.com");
        exchange_url.add("https://www.BTCBOX.com");
        exchange_url.add("https://www.Kryptono.exchange");
        exchange_url.add("https://www.UEX.com");
        exchange_url.add("https://www.Bitlish.com");
        exchange_url.add("https://www.Sistemkoin.com");
        exchange_url.add("https://www.Bitstamp.com");
        exchange_url.add("https://www.Bitbank.com");
        exchange_url.add("https://www.CoinTiger.com");
        exchange_url.add("https://www.CoinBene.com");
        exchange_url.add("https://www.LATOKEN.com");
        exchange_url.add("https://www.Bitstamp.com");
        exchange_url.add("https://www.Bittrex.com");
        exchange_url.add("https://www.Poloniex.com");
        exchange_url.add("https://www.CoinEgg.com");
        exchange_url.add("https://www.Exmo.com");
        exchange_url.add("https://www.Hotbit.com");
        exchange_url.add("https://www.bitFlyer.com");
        exchange_url.add("https://www.B2BX.com");
        exchange_url.add("https://www.Rfinex.com");
        exchange_url.add("https://www.CPDAX.com");
        exchange_url.add("https://www.C2CX.com");
        exchange_url.add("https://www.Livecoin.com");
        exchange_url.add("https://www.BtcTrade.im");
        exchange_url.add("https://www.xBTCe.com");
        exchange_url.add("https://www.Coinone.com");
        exchange_url.add("https://www.Kucoin.com");
        exchange_url.add("https://www.BitBay.com");
        exchange_url.add("https://www.Gemini.com");
        exchange_url.add("https://www.Coinbe.net");
        exchange_url.add("https://www.InfinityCoin.exchange");
        exchange_url.add("https://www.Tradebytrade.com");
        exchange_url.add("https://www.BiteBTC.com");
        exchange_url.add("https://www.Coinsquare.com");
        exchange_url.add("https://www.HADAX.com");
        exchange_url.add("https://www.Coinroom.com");
        exchange_url.add("https://www.Mercatox.com");
        exchange_url.add("https://www.Coinhub.com");
        exchange_url.add("https://www.BigONE.com");
        exchange_url.add("https://www.BitShares.org");
        exchange_url.add("https://www.CEX.IO");
        exchange_url.add("https://www.ChaoEX.com");
        exchange_url.add("https://www.Bitsane.com");
        exchange_url.add("https://www.BitForex.com");
        exchange_url.add("https://www.LocalTrade.com");
        exchange_url.add("https://www.Bitinka.com");
        exchange_url.add("https://www.Liqui.io");
        exchange_url.add("https://www.Liquid.com");
        exchange_url.add("https://www.BTC-Alpha.com");
        exchange_url.add("https://www.Instantbitex.com");
        exchange_url.add("https://www.Cryptopia.com");
        exchange_url.add("https://www.GOPAX.com");
        exchange_url.add("https://www.Korbit.com");
        exchange_url.add("https://www.itBit.com");
        exchange_url.add("https://www.Indodax.com");
        exchange_url.add("https://www.Vebitcoin.com");
        exchange_url.add("https://www.Allbit.com");
        exchange_url.add("https://www.Indodax.com");
        exchange_url.add("https://www.BtcTurk.com");
        exchange_url.add("https://www.Ovis.com");
        exchange_url.add("https://www.BTCC.com");
        exchange_url.add("https://www.IDEX.market");
        exchange_url.add("https://www.Ethfinex.com");
        exchange_url.add("https://www.QuadrigaCX.com");
        exchange_url.add("https://www.CoinExchange.io");
        exchange_url.add("https://www.Tidex.com");
        exchange_url.add("https://www.https://www.negociecoins.com/br");
        exchange_url.add("https://www.CryptoBridge.com");
        exchange_url.add("https://www.LakeBTC.com");
        exchange_url.add("https://www.Bancor.network");
        exchange_url.add("https://www.QBTC.com");
        exchange_url.add("https://www.WEX.com");
        exchange_name.add("Binance");
        exchange_name.add("OKEx");
        exchange_name.add("Huobi");
        exchange_name.add("Bithumb");
        exchange_name.add("ZB.COM");
        exchange_name.add("Bitfinex");
        exchange_name.add("HitBTC");
        exchange_name.add("Bit-Z");
        exchange_name.add("Bibox");
        exchange_name.add("Coinsuper");
        exchange_name.add("BCEX");
        exchange_name.add("LBank");
        exchange_name.add("DigiFinex");
        exchange_name.add("Upbit");
        exchange_name.add("Coinbase Pro");
        exchange_name.add("Simex");
        exchange_name.add("OEX");
        exchange_name.add("Kraken");
        exchange_name.add("Cryptonex");
        exchange_name.add("BitMart");
        exchange_name.add("Allcoin");
        exchange_name.add("IDAX");
        exchange_name.add("RightBTC");
        exchange_name.add("IDCM");
        exchange_name.add("YoBit");
        exchange_name.add("DragonEX");
        exchange_name.add("Gate.io");
        exchange_name.add("Fatbtc");
        exchange_name.add("Exrates");
        exchange_name.add("CoinsBank");
        exchange_name.add("BTCBOX");
        exchange_name.add("Kryptono");
        exchange_name.add("UEX");
        exchange_name.add("Bitlish");
        exchange_name.add("Sistemkoin");
        exchange_name.add("Bitstamp");
        exchange_name.add("Bitbank");
        exchange_name.add("CoinTiger");
        exchange_name.add("CoinBene");
        exchange_name.add("LATOKEN");
        exchange_name.add("Bitstamp");
        exchange_name.add("Bittrex");
        exchange_name.add("Poloniex");
        exchange_name.add("CoinEgg");
        exchange_name.add("Exmo");
        exchange_name.add("Hotbit");
        exchange_name.add("B2BX");
        exchange_name.add("bitFlyer");
        exchange_name.add("Rfinex");
        exchange_name.add("CPDAX");
        exchange_name.add("C2CX");
        exchange_name.add("Livecoin");
        exchange_name.add("BtcTrade.im");
        exchange_name.add("xBTCe");
        exchange_name.add("Coinone");
        exchange_name.add("Kucoin");
        exchange_name.add("BitBay");
        exchange_name.add("Gemini");
        exchange_name.add("Coinbe");
        exchange_name.add("InfinityCoin Exchange");
        exchange_name.add("Trade By Trade");
        exchange_name.add("BiteBTC");
        exchange_name.add("Coinsquare");
        exchange_name.add("HADAX");
        exchange_name.add("Coinroom");
        exchange_name.add("Mercatox");
        exchange_name.add("Coinhub");
        exchange_name.add("BigONE");
        exchange_name.add("BitShares Exchange");
        exchange_name.add("CEX.IO");
        exchange_name.add("ChaoEX");
        exchange_name.add("Bitsane");
        exchange_name.add("BitForex");
        exchange_name.add("LocalTrade");
        exchange_name.add("Bitinka");
        exchange_name.add("Liqui");
        exchange_name.add("Liquid");
        exchange_name.add("BTC-Alpha");
        exchange_name.add("Instant Bitex");
        exchange_name.add("Cryptopia");
        exchange_name.add("GOPAX");
        exchange_name.add("Korbit");
        exchange_name.add("itBit");
        exchange_name.add("Indodax");
        exchange_name.add("Vebitcoin");
        exchange_name.add("Allbit");
        exchange_name.add("Coindeal");
        exchange_name.add("BtcTurk");
        exchange_name.add("Ovis");
        exchange_name.add("BTCC");
        exchange_name.add("IDEX");
        exchange_name.add("Ethfinex");
        exchange_name.add("QuadrigaCX");
        exchange_name.add("CoinExchange");
        exchange_name.add("Tidex");
        exchange_name.add("Negocie Coins");
        exchange_name.add("CryptoBridge");
        exchange_name.add("LakeBTC");
        exchange_name.add("Bancor Network");
        exchange_name.add("QBTC");
        exchange_name.add("WEX");


    }

    public static void get_crypto_points() {
        long startTime = System.nanoTime();
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();
        String f = "bitcoin";
        //("NAMD!!! "+f);
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
                    //("PRICE "+graph_high);
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




        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //("CRYPTO POINTS TIME IS " + duration / 1000000000 + " seconds");



    }

    public static void get_stock_change() {
        stock_kings_symbollist.add("gsat");
        stock_kings_symbollist.add("aapl");
        stock_kings_symbollist.add("dpw");
        stock_kings_symbollist.add("fb");
        stock_kings_symbollist.add("goog");
        String symbol= null;
        for(int z = 0; z < stock_kings_symbollist.size();++z) {
            symbol = String.valueOf(stock_kings_symbollist.get(z));
            Document d = null;
            try {
                d = Jsoup.connect("https://www.nasdaq.com/symbol/" + symbol).userAgent("Mozilla").timeout(10 * 100000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element change = d.getElementById("qwidget_percent");
            //("PERCENT CHANGE" + change);
        }

    }

    public static void get_masternodes(){
    Document m =null;
        try {
            m = Jsoup.connect("https://masternodes.online").userAgent("Mozilla").timeout(10 * 100000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element c = m.getElementById("coins");
        Elements tb = c.select("tbody");
        Elements tr =tb.select("tr");
        for(int i=0;i<10;i++){
            Element poo= tr.get(i);
            Elements r =poo.select("td");
            String[] splited = r.get(2).text().split(" ");
            masternode_name.add(splited[0]);
            masternode_symbol.add(splited[1].replace("(","").replace(")",""));
            masternode_percent_change.add(r.get(4).text().replace("%",""));
            String temp=r.get(5).text().replace("$","").replace(",","");
            Double add = Double.parseDouble(temp);
            String a = String.format("%.0f", add);
            String added = null;
            if (add > 1000) {
                added = a.substring(0,3) + " M";
            } else {
                added = a.substring(0,3) + " TH";
            }
            masternode_marketcap.add(added);
            masternode_node_count.add(r.get(8).text().replace(",",""));
            masternode_purchase_value.add(r.get(10).text().replace("$",""));
            //(masternode_name.get(i)+" "+masternode_symbol.get(i)+" "+masternode_percent_change.get(i)+" "+masternode_marketcap.get(i)+" "+masternode_node_count.get(i)+" "+masternode_purchase_value.get(i));
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
                formatted = f.parse(y);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            try {
                formatted2 = f.parse(z);
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
        for (int i = 1; i < tr.size(); i++) {
            Element row = tr.get(i);
            Elements cols = row.select("td");
            ipo_date.add(cols.get(0).text());
            ipo_name.add(cols.get(1).text());
            ipo_range.add(cols.get(2).text());
            ipo_volume.add(cols.get(3).text());
        }
        }

    public static void find_urls(){
        String name="https://www.yahoo";
        final String comURL = name+".com";
        final String ioURL = name+".io";
        final String techURL = name+".tech";
        ArrayList URLs = new ArrayList();
        URLs.add(comURL);
        URLs.add(ioURL);
        URLs.add(techURL);
        for(int i =0;i<URLs.size();i++) {
            try {
                URL url = new URL((String) URLs.get(i));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("HEAD");
                con.connect();
                //("con.getResponseCode() IS : " + con.getResponseCode());
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //("Sucess");
                }
            } catch (Exception e) {
                e.printStackTrace();
                //("fail");
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
                            st = curent.getTextContent().toString();
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

}
