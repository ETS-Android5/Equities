package airhawk.com.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static airhawk.com.myapplication.App_Variables._1_Day_Chart;
import static airhawk.com.myapplication.App_Variables._30Days;
import static airhawk.com.myapplication.App_Variables._7Days;
import static airhawk.com.myapplication.App_Variables._90Days;
import static airhawk.com.myapplication.App_Variables._AllDays;
import static airhawk.com.myapplication.App_Variables.all_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.alt_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.App_Variables.graph_date;
import static airhawk.com.myapplication.App_Variables.graph_high;
import static airhawk.com.myapplication.App_Variables.graph_low;
import static airhawk.com.myapplication.App_Variables.graph_market_cap;
import static airhawk.com.myapplication.App_Variables.graph_volume;

public class TestMethods {
    static ArrayList cryptovolumelist = new ArrayList();
    static ArrayList crypto_winners_changelist = new ArrayList();
    static ArrayList crypto_winners_namelist = new ArrayList();
    static ArrayList crypto_winners_symbollist = new ArrayList();
    static ArrayList crypto_losers_changelist = new ArrayList();
    static ArrayList crypto_losers_namelist = new ArrayList();
    static ArrayList crypto_losers_symbollist = new ArrayList();
    static ArrayList stock_win_symbollist = new ArrayList();
    static ArrayList stock_win_namelist = new ArrayList();
    static ArrayList stock_win_changelist = new ArrayList();
    static ArrayList stock_losers_symbollist = new ArrayList();
    static ArrayList stock_losers_namelist = new ArrayList();
    static ArrayList stock_losers_changelist = new ArrayList();
    public static void main(String[] args) {

        Document stock_data = null;
        Document crypto_data = null;
        try {
            crypto_data = Jsoup.connect("https://www.worldcoinindex.com/").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements crypto_content = crypto_data.getElementsByClass("top-marketcap");
        String cryptext = crypto_content.text();
        String[] cryptarray = cryptext.split(" ");
        all_market_cap_amount = cryptarray[4];

        Elements btc_mc = crypto_data.getElementsByClass("row row-body zoomer");
        Elements xxdd = btc_mc.select("tbody");
        Elements cc = xxdd.select("span");
        String c = cc.text();
        String ce[] = c.split(" ");
        btc_market_cap_change = ce[4];
        String are = xxdd.text();
        String aren[] = are.split(" ");
        String ca = cryptarray[4].replace("B", "");
        String ba = aren[14].replace("B", "");
        Double dca = Double.parseDouble(ca);
        Double dba = Double.parseDouble(ba);
        Double dcadba = dca - dba;
        Float t = dcadba.floatValue();
        String altmc = t.toString();
        btc_market_cap_amount = aren[14];
        alt_market_cap_amount = altmc + "B";
        try {
            stock_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

//CRYPTO LOSERS ARRAYS
        Elements ffsd = stock_data.getElementsByClass("table-responsive");
        Elements tr = stock_data.select("div#losers-24h");
        Elements lose1 = tr.select("td.text-left");
        Elements lose2 = tr.select("td[data-sort]");
        for (Element s :lose1){
            crypto_losers_symbollist.add(s.text());
        }
        for(int i=0; i<lose2.size(); i++) {
            if (i % 2 == 0) {
                crypto_losers_namelist.add(lose2.get(i).text());
            } else {
                crypto_losers_changelist.add(lose2.get(i).text());
            }
        }
//CRYPTO WINNERS ARRAYS
        Element l = ffsd.get(1);
        Elements xx = l.select("tbody");
        Elements xxxd = xx.select("td[data-usd]");//Get's percentage change
        Elements ax = xx.select("tr");
        Elements name_change = xx.select("img[src]");
        for (Element crypto_symbol : ax)
        {
            String symbol = crypto_symbol.select("td.text-left").text();
            crypto_winners_symbollist.add(symbol);
        }
        for (Element crypto_name : name_change)
        {
            String name = crypto_name.attr("alt");
            crypto_winners_namelist.add(name);
        }
        for (Element crypto_change : xxxd)
        {
            crypto_winners_changelist.add(crypto_change.text());
        }
//STOCK WINNERS ARRAYS
        Document ss = null;
        try {
            ss = Jsoup.connect("http://money.cnn.com/data/hotstocks/sp1500/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ddd = ss.getElementsByClass("wsod_dataTable wsod_dataTableBigAlt");
        Element ff = ddd.get(1);
        Elements a = ff.select("a");
        Elements b = ff.select("span[title]");
        Elements aa = ff.select("span.posData");

        for (Element stock_symbol : a)
        {
            String symbol = stock_symbol.select("a.wsod_symbol").text();
            stock_win_symbollist.add(symbol);

        }
        for (Element stock_name : b)
        {
            String name = stock_name.text();
            if (name.isEmpty()){}
            else
                {
                stock_win_namelist.add(name);
            }
        }
        for (Element stock_change : aa)
        {
            String change = stock_change.text();
            if (change.isEmpty()){}
            else
                {
            if(change.contains("%")){
                stock_win_changelist.add(change);
            }

            }
        }
//STOCK LOSERS ARRAYS
        Element fl = ddd.get(2);
        Elements al = fl.select("a");
        Elements bl = fl.select("span[title]");
        Elements aal = fl.select("span.negData");
        for(Element x:al){stock_losers_symbollist.add(x.text());}
        for(Element x:bl){stock_losers_namelist.add(x.text());}
        for(int i =0; i<aal.size(); i++) {
            if (i % 2 == 0) {
                // This is point amount
            } else {
                stock_losers_changelist.add(aal.get(i).text());
            }
        }
//STOCK VOLUME LEADERS ARRAY
        //ADD VOLUME LOGIC NEXT PUSH
        Document sv = null;
        try {
            sv = Jsoup.connect("https://finance.yahoo.com/most-active/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }


        }
}












