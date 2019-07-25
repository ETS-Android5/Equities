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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Spliterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static equities.com.myapplication.Constructor_App_Variables.*;
import static equities.com.myapplication.Service_Main_Equities.*;
import static equities.com.myapplication.Service_Main_Equities.crypto_losers_changelist;

public class Test_Methods {
    static ArrayList exchange_url = new ArrayList<>();
    static ArrayList exchange_name = new ArrayList<>();
    private static Context mContext;
    static String cryptopia_list;
    static Document sss =null;

    public static void main(String[] args) {
        getStock_Data();
       }



    public static void toDouble(ArrayList array, String string){
        Double doub = Double.parseDouble(string);
        DecimalFormat DollarPlus = new DecimalFormat("##.##");
        DecimalFormat Between = new DecimalFormat(".##");
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
        array.add(string);
    }

    public static void getCryptoData(){
        //Scrape data and if any Array is 0 use API as backup
        try {
            crypto_data = Jsoup.connect("https://coinmarketcap.com/gaiers-losers/").timeout(10 * 10000).get();
            Elements losers_table = crypto_data.getElementsByClass("table-responsive");
            Elements losers = crypto_data.select("div#losers-24h");
            Elements loser_symbol = losers.select("td.text-left");
            Elements loser_name_change = losers.select("td[data-sort]");
            for (Element s : loser_symbol) {
                crypto_losers_symbollist.add(s.text());
            }
            Elements price = losers.select("a.price");
            for(Element x : price){
                String url = x.attr("data-usd");
                toDouble(crypto_losers_pricelist,url);
            }
            for (int i = 0; i < loser_name_change.size(); i++) {
                if (i % 2 == 0) {
                    crypto_losers_namelist.add(loser_name_change.get(i).text());
                } else {
                    crypto_losers_changelist.add(loser_name_change.get(i).text());
                }
            }
            Element winners_table = losers_table.get(2);
            Elements tbody = winners_table.select("tbody");
            Elements winners = crypto_data.select("div#gainers-24h");
            Elements winner_change = tbody.select("td[data-usd]");//Get's percentage change
            Elements winner_symbol = tbody.select("tr");
            Elements winner_name = tbody.select("img[src]");
            for (Element crypto_symbol : winner_symbol) {
                String symbol = crypto_symbol.select("td.text-left").text();
                crypto_winners_symbollist.add(symbol);
            }
            Elements link = winners.select("a.price");
            for(Element x : link){
                String url = x.attr("data-usd");
                Double d =Double.parseDouble(url);
                DecimalFormat df = new DecimalFormat("#.##");
                df.format(d);
                crypto_win_pricelist.add("$ "+d);}
            for (Element crypto_name : winner_name) {
                String name = crypto_name.attr("alt");
                crypto_winners_namelist.add(name);
            }
            for (Element crypto_change : winner_change) {
                crypto_winners_changelist.add(crypto_change.text());
            }
            //System.out.println(crypto_win_pricelist);
        } catch (IOException e) {
            //Use alternative method
            try{
                crypto_data = Jsoup.connect("https://www.coingecko.com/en/coins/trending").timeout(10 * 10000).get();
                Elements tbody = crypto_data.select("tbody");
                Elements winner_change = tbody.select("tr");
                for(Element x :winner_change){
                    String symbol = x.select("span").get(0).text();
                    String name = x.select("span").get(1).text();
                    String price = x.select("span").get(4).text();
                    String change = x.select("span").get(5).text();
                    if(crypto_winners_namelist.size()<30){
                    crypto_winners_symbollist.add(symbol);
                    crypto_winners_namelist.add(name);
                    crypto_winners_changelist.add(change);
                    crypto_win_pricelist.add(price);
                        //System.out.println("WINNERS "+name+" "+symbol+" "+price+" "+change);
                    }else{
                        crypto_losers_symbollist.add(symbol);
                        crypto_losers_namelist.add(name);
                        crypto_losers_changelist.add(change);
                        crypto_losers_pricelist.add(price);
                        //System.out.println("LOSERS "+name+" "+symbol+" "+price+" "+change);

                    }

                }
                System.out.println(crypto_winners_namelist.size());
            }
            catch (IOException i){
                //i.printStackTrace();
                //USE THIRD OPTION api IF NECCESSARY
            }
        }
        //GETTING CRYPTO MARKET CAP LEADERS
        try {
            crypto_data = Jsoup.connect("https://coinmarketcap.com/").timeout(10 * 10000).get();
            Elements table = crypto_data.select("tbody");
            Elements tr = table.select("tr");
            for (Element t :tr){
                String symbol_name = t.select("td").get(1).text();
                String [] split = symbol_name.split(" ");
                String symbol = split[0].replace(" ","");
                String name = symbol_name.replace(split[0],"").replace(" ","");
                String price = t.select("td").get(3).text();
                String change = t.select("td").get(6).text();
                String cap = t.select("td").get(2).text();
                crypto_kings_symbolist.add(symbol);
                crypto_kings_namelist.add(name);
                crypto_kings_pricelist.add(price.replace("$",""));
                crypto_kings_changelist.add(change+"%");
                double d = Double.parseDouble(cap.replace("$","").replace(",","" +
                        ""));
                DecimalFormat df = new DecimalFormat("0.00");
                df.setMaximumFractionDigits(2);
                cap =df.format(d);
                int l =cap.length();
                long ti = 1000000000000L;
                if (l<=12){
                    cap= String.valueOf(d/1000000);
                    crypto_kings_marketcaplist.add(cap.substring(0,3)+" M");}
                if (l>12){cap= String.valueOf(d/1000000000);
                    crypto_kings_marketcaplist.add(cap.substring(0,3)+" B");}
                if (l>15){cap= String.valueOf(d/ti);
                    crypto_kings_marketcaplist.add(cap.substring(0,3)+" T");}

                System.out.println("X "+symbol+" "+name+" "+price+" "+change+" "+cap);
            }
        }catch (IOException n){
            //USING ALTERNATIVE SITE

            try {
                crypto_data = Jsoup.connect("https://www.coingecko.com/").timeout(10 * 10000).get();
            }catch (IOException o){

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
            for(int i=0;i<tr.size();i++){
                String symbol = tr.get(i).select("td").get(0).text();
                String name = tr.get(i).select("td").get(1).text();
                String price = tr.get(i).select("td").get(3).text().replace("+","");
                String change = tr.get(i).select("td").get(4).text().replace("+","");
                stock_win_symbollist.add(symbol);
                stock_win_namelist.add(name);
                stock_win_pricelist.add(price);
                stock_win_changelist.add(change);
                System.out.println("FIRST METHOD WINNERS"+symbol+" "+name+" "+price+" "+change);

            }
//LOSERS
            sv = Jsoup.connect("https://finance.yahoo.com/losers").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbodyl = sv.select("tbody");
            Elements trl = tbodyl.select("tr");
            for (int il = 0; il < trl.size(); il++) {
                String lsymbol = trl.get(il).select("td").get(0).text();
                String lname = trl.get(il).select("td").get(1).text();
                String lprice = trl.get(il).select("td").get(3).text().replace("+", "");
                String lchange = trl.get(il).select("td").get(4).text().replace("+", "");
                stock_losers_symbollist.add(lsymbol);
                stock_losers_namelist.add(lname);
                stock_losers_pricelist.add(lprice);
                stock_losers_changelist.add(lchange);
                System.out.println("FIRST METHOD  LOSERS"+lsymbol + " " + lname + " " + lprice + " " + lchange);
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
                stock_kings_pricelist.add(price);
                stock_kings_changelist.add(change);
                //System.out.println("MAIN KINGS METHOD "+name+" "+symbol+" "+price+" "+change);
            }
        } catch (IOException z) {
            //USE ALTERNATIVE SITE FOR STOCK KINGS
            try {
                sv = Jsoup.connect("http://www.dogsofthedow.com/largest-companies-by-market-cap.htm").userAgent("Opera").timeout(10 * 10000).get();
                Elements tbody =sv.select("tbody");
                Elements tbody2 =tbody.select("tbody");
                Elements tr =tbody2.select("tr");
                for (int i = 15; i <= 24; i++) {
                    Element td0 = tr.get(i).select("td").get(0);
                    Element td1 = tr.get(i).select("td").get(1);
                    Element td2 = tr.get(i).select("td").get(3);
                    Element td3 = tr.get(i).select("td").get(4);
                    String remove =td2.text().replace(",","");
                    double add = Double.parseDouble(remove);
                    int value = (int)Math.round(add);
                    String added = String.valueOf(value);
                    if (add > 1000) {
                        added = added + " T";
                    } else
                    {
                        added = added + " B";
                    }
                    try{
                        sv = Jsoup.connect("https://money.cnn.com/quote/quote.html?symb="+td0.text()).timeout(10 * 10000).get();
                        Elements t =sv.select("tbody");
                        Elements tx =t.select("tr");
                        String td = tx.select("td").get(0).text();
                        String[] split = td.split(" ");
                        stock_kings_changelist.add(split[0]);
                        //System.out.println("SPECIFIC QUOTE "+split[0]);

                    }catch (IOException d){}
                    stock_kings_namelist.add(td1.text());
                    stock_kings_symbollist.add(td0.text());
                    stock_kings_changelist.add(added);
                    //stock_kings_ipdown.add(td3.text());

//                    System.out.println(i-14+" "+td0.text()+" "+td1.text()+" "+added);
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
            for (Element stock_symbol : a) {
                String symbol = stock_symbol.select("a.wsod_symbol").text();
                stock_win_symbollist.add(symbol); }
            for (Element stock_name : b) {
                String name = stock_name.text();
                if (name.isEmpty()) {
                } else {
                    stock_win_namelist.add(name); } }
            for (Element stock_price : bb) {
                String price = stock_price.text();
                if (price.isEmpty()) {
                } else {
                    if (price.contains("+")||price.contains("-")||price.contains("%")) {
                    }else{
                        stock_win_pricelist.add(price);} } }
            for (Element stock_change : aa) {
                String change = stock_change.text();
                if (change.isEmpty()) {
                } else {
                    if (change.contains("%")) {
                        stock_win_changelist.add(change); } } }
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
            System.out.println("THIS IS FINAL METHOD " +stock_losers_symbollist.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }














    public static void get_stock_kings_points() {


        for(int i=0;i<=stock_kings_symbollist.size();i++){
            Document cap =null;
            try{ cap =Jsoup.connect("https://finance.yahoo.com/quote/DPW?p=DPW").timeout(10 *10000).get();
            } catch (IOException e){ e.printStackTrace(); }
            Elements test = cap.select("div[data-reactid='50']");

            String foofoo =test.text().toString().replace("(","").replace(")","").replace("%","");
            String[] foo = foofoo.split(" ");
            String f =foo[1];
            System.out.println("Here is updown "+f);}
    }

    public static void getWorldMarkets(){
        Document euro=null;
        try{
            euro =Jsoup.connect("https://money.cnn.com/data/world_markets/europe/").timeout(10 *10000).get();
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
        }catch (IOException e){
            try{
                Document asia=null;
                asia =Jsoup.connect("https://finance.yahoo.com/world-indices").timeout(10 *10000).get();

            }catch (IOException z){}
            e.printStackTrace();
        }

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
        bov_name = as.get(4).text();
        Elements as1 = z.select("h3>span");
        sp_amount =as1.get(0).text();
        dow_amount=as1.get(1).text();
        bov_amount =as1.get(2).text();
        Elements as2 =z.select("span>span");
        sp_change =as2.get(2).text();
        dow_change=as2.get(3).text();
        bov_change =as2.get(4).text();
        if (sp_change.contains("-")){
            sp_flow=true;
        }
        if (dow_change.contains("-")){
            dow_flow=true;
        }
        if (bov_change.contains("-")){
            nd_flow=true;
        }
        System.out.println(sp_name+" "+sp_amount+" "+sp_change);
        System.out.println(dow_name+" "+dow_amount+" "+dow_change);
        System.out.println(bov_name +" "+ bov_amount +" "+ bov_change);
        System.out.println(as6.text());
    }

    public static void getTESTSavedEquities(){

                Document caps = null;

                    try {
                        caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + "bitcoin").timeout(10 * 10000).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Element as = caps.getElementsByClass("details-panel-item--header flex-container").first();
                    Elements d = as.select("span:eq(1)");
                    String f = d.get(2).text();;

                    current_percentage_change.add(f);
System.out.println(f);
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
                d = Jsoup.connect("https://www.bovespa.com/symbol/" + symbol).userAgent("Mozilla").timeout(10 * 100000).get();
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
        Elements n = tb.get(1).select("tr");
            for(int x =0; x<10;x++){
                Elements k = n.get(x).select("td");
                String z = k.text().replace("%","").replace("$","").replace(",","");
                String[] split = z.split(" ");
                if(split.length<12){
                    masternode_name.add(split[0]);
                    masternode_symbol.add(split[1].replace("(","").replace(")",""));
                    masternode_percent_change.add(split[3]);
                    Double add = Double.parseDouble(split[6]);
                    String a = String.format("%.0f", add);
                    String added = null;
                    if (add >=10000 ) {
                        added = a.substring(0, 3) + " B";
                    }
                    if (add > 1000) {
                        added = a.substring(0, 3) + " M";
                    } else {
                        added = a.substring(0, 3) + " TH";
                    }
                    masternode_marketcap.add(added);
                    masternode_node_count.add(split[8]);
                    masternode_purchase_value.add(split[10]);}
                    else{
                    masternode_name.add(split[0]);
                    masternode_symbol.add(split[2].replace("(","").replace(")",""));
                    masternode_percent_change.add(split[4].replace("$",""));
                    Double add = Double.parseDouble(split[6]);
                    String a = String.format("%.0f", add);
                    String added = null;
                    if (add >=10000 ) {
                        added = a.substring(0, 3) + " B";
                    }
                    if (add > 1000) {
                        added = a.substring(0, 3) + " M";
                    } else {
                        added = a.substring(0, 3) + " TH";
                    }
                    masternode_marketcap.add(added);
                    masternode_node_count.add(split[9]);
                    masternode_purchase_value.add(split[11]);}
                System.out.println("THIS IS "+x+" "+masternode_name.get(x)+" "+masternode_symbol.get(x)+" "+masternode_percent_change.get(x)+" "+masternode_marketcap.get(x)+" "+masternode_node_count.get(x)+" "+masternode_purchase_value.get(x));

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
                if(y.isEmpty()){formatted = f.parse("December 31, 2019");}else{
                formatted = f.parse(y);}
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            try {
                if(z.isEmpty()){formatted2 = f.parse("December 31, 2019");}else{
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
            System.out.println(ipo_date+" "+ipo_name+" "+ipo_range+" "+ipo_volume);
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


    public static void getCrypto_Winners_Losers() {
        try {
            crypto_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements ffsd = crypto_data.getElementsByClass("table-responsive");
        Elements tr = crypto_data.select("div#losers-24h");
        Elements lose1 = tr.select("td.text-left");
        Elements lose2 = tr.select("td[data-sort]");
        Elements links = tr.select("a.price");
        Element link = links.first();
        for(Element x : links){
        String url = x.attr("data-usd");
        System.out.println("url = " + url);}

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
        for (int i=0;i<crypto_losers_namelist.size();i++){
            System.out.println(crypto_losers_namelist.get(i)+" "+crypto_losers_symbollist.get(i)+" "+crypto_losers_changelist.get(i));
        }
//CRYPTO WINNERS ARRAYS
        Element l = ffsd.get(2);
        Elements xx = l.select("tbody");
        Elements ca = crypto_data.select("div#gainers-24h");
        Elements xxxd = xx.select("td[data-usd]");//Get's percentage change
        Elements ax = xx.select("tr");
        Elements links1 = ca.select("a.price");
        for(Element x : links1){
            String url = x.attr("data-usd");
            System.out.println("win" +
                    " = " + url);}
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

        for (int i=0;i<crypto_winners_namelist.size();i++){
       //     System.out.println(crypto_winners_namelist.get(i)+" "+crypto_winners_symbollist.get(i)+" "+crypto_win_pricelist.get(i)+" "+crypto_winners_changelist.get(i));
        }
    }

}
