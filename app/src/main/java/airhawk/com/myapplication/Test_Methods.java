package airhawk.com.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static airhawk.com.myapplication.Activity_Main.get_current_aequity_price;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static airhawk.com.myapplication.Constructor_App_Variables._AllDays;
import static airhawk.com.myapplication.Constructor_App_Variables.all_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.alt_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_name;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_low;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_market_cap;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_change;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_name;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_change;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_name;

public class Test_Methods {
    //static ArrayList crypto_volume_symbollist = new ArrayList();
    static ArrayList crypto_volume_namelist = new ArrayList();
    static ArrayList crypto_volume_volumelist = new ArrayList();
    static ArrayList stock_volume_symbollist = new ArrayList();
    static ArrayList stock_volume_namelist = new ArrayList();
    static ArrayList stock_volume_volumelist = new ArrayList();
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
//CRYPTO LOSERS ARRAYS
       try {
            stock_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").get();
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
        Element l = ffsd.get(1);
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
//STOCK VOLUME LEADERS ARRAY
        Document sv = null;
        try {
            sv = Jsoup.connect("https://finance.yahoo.com/most-active/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements table_body = sv.select("tbody");
        Elements table_row = table_body.select("tr");
        Elements table_divider = table_row.select("td");
        for (int i = 4; i < table_divider.size(); i += 11) {
            stock_volume_namelist.add(table_divider.get(i).text());
        }
        for (int i = 3; i < table_divider.size(); i += 11) {
            stock_volume_symbollist.add(table_divider.get(i).text());
        }
        for (int i = 8; i < table_divider.size(); i += 11) {
            stock_volume_volumelist.add(table_divider.get(i).text());
        }




//CRYPTO VOLUME LEADERS ARRAY
        Document bb = null;
        try {
            bb = Jsoup.connect("https://cryptocoincharts.info/coins/info").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements h =bb.getElementsByClass("d_row panel-group");
        String sax =h.text();

        System.out.println("THIS IS IT "+h);

        Elements j = bb.getElementsByClass("coins-name");
        String jj =j.text();
        System.out.println("THIS IS IT 2 "+jj);


        crypto_volume_volumelist.add(1);
        crypto_volume_namelist.add("Taco");



        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();
        Document doc = null;
        String market_name="TRON";
        try {
            doc = Jsoup.connect("https://coinmarketcap.com/currencies/" + market_name + "/historical-data/?start=20000101&end=" + sdf.format(begindate)).get();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element price = doc.getElementById("quote_price");
        String v = price.text();
        String[] splitz = v.split(" ");
        get_current_aequity_price=splitz[0];
        Elements divs = doc.select("table");
        for (Element tz : divs) {
            Elements tds = tz.select("td");
            Elements s = tz.getElementsByClass("text-right");
            for (Element sss : s) {
                Elements p = sss.select("td[data-format-fiat]");
                String g = p.text();
                String[] splited = g.split("\\s+");
                if (v != null && !g.isEmpty()) {
                    graph_high.add(splited[3]);
                    graph_low.add(splited[2]);
                }
                Elements pn = sss.select("td[data-format-market-cap]");
                String vp = pn.text();
                String[] split = vp.split("\\s+");
                if (vp != null && !vp.isEmpty()) {
                    graph_volume.add(split[0]);
                    graph_market_cap.add(split[1]);
                }
            }
            for (Element bbb : tds) {
                Elements gdate = bbb.getElementsByClass("text-left");
                String result0 = gdate.text();
                if (result0 != null && !result0.isEmpty()) {
                    graph_date.add(String.valueOf(result0));
                }
            }
        }
        List<String> numbers = graph_high;
        System.out.println(graph_high);
        _AllDays = numbers;


        Document z =null;
        try{
            z=Jsoup.connect("https://finance.yahoo.com").get();
        }
        catch(IOException e){
        e.printStackTrace();
        }
        Element table =z.getElementById("Lead-2-FinanceHeader-Proxy");
        Elements as =table.select("a");
        Elements as2 =table.select("span");
        sp_name=as.get(0).text();
        dow_name=as.get(2).text();
        nas_name=as.get(4).text();
        sp_amount=as2.get(2).text();
        sp_change=as2.get(5).text();
        dow_amount=as2.get(6).text();
        dow_change=as2.get(9).text();
        nas_amount=as2.get(10).text();
        nas_change=as2.get(13).text();
    }




}












