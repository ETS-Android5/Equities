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
import java.util.Scanner;

import static airhawk.com.myapplication.App_Variables._1_Day_Chart;
import static airhawk.com.myapplication.App_Variables._30Days;
import static airhawk.com.myapplication.App_Variables._7Days;
import static airhawk.com.myapplication.App_Variables._90Days;
import static airhawk.com.myapplication.App_Variables._AllDays;
import static airhawk.com.myapplication.App_Variables.all_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.alt_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.App_Variables.cry1;
import static airhawk.com.myapplication.App_Variables.cry2;
import static airhawk.com.myapplication.App_Variables.cry3;
import static airhawk.com.myapplication.App_Variables.cry4;
import static airhawk.com.myapplication.App_Variables.cry5;
import static airhawk.com.myapplication.App_Variables.cry_1;
import static airhawk.com.myapplication.App_Variables.cry_2;
import static airhawk.com.myapplication.App_Variables.cry_3;
import static airhawk.com.myapplication.App_Variables.cry_4;
import static airhawk.com.myapplication.App_Variables.cry_5;
import static airhawk.com.myapplication.App_Variables.cry_vol1;
import static airhawk.com.myapplication.App_Variables.cry_vol2;
import static airhawk.com.myapplication.App_Variables.cry_vol3;
import static airhawk.com.myapplication.App_Variables.cry_vol4;
import static airhawk.com.myapplication.App_Variables.cry_vol5;
import static airhawk.com.myapplication.App_Variables.graph_date;
import static airhawk.com.myapplication.App_Variables.graph_high;
import static airhawk.com.myapplication.App_Variables.graph_low;
import static airhawk.com.myapplication.App_Variables.graph_market_cap;
import static airhawk.com.myapplication.App_Variables.graph_volume;
import static airhawk.com.myapplication.App_Variables.market_name;

public class TestMethods {
    static ArrayList cryptoarrayvolumellist = new ArrayList();
    static ArrayList cryptoarraynamellist = new ArrayList();

    public static void main(String[] args) {


        Document volume_data = null;
        Document stock_data = null;
        Document crypto_data = null;
        Long start_marketcaps = null;
        Long end_marketcaps;
        Long start_winlosecaps = null;
        Long end_winlosecaps;
        Long start_volume = null;
        Long end_volume;

        Long start_cmc =null;
        Long end_cmc;
        try {
            crypto_data = Jsoup.connect("https://www.worldcoinindex.com/").timeout(10 * 10000).get();
            start_marketcaps = System.currentTimeMillis();
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
        end_marketcaps= System.currentTimeMillis();
        System.out.println(end_marketcaps-start_marketcaps+" MarketCap time in milliseconds"); //Milli Secs
        System.out.print((end_marketcaps-start_marketcaps)/1000); //Secs
        System.out.println(" Seconds for "+ cryptarray.length + " entries");
        try {

            stock_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").get();
            start_winlosecaps = System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ffsd = stock_data.getElementsByClass("table-responsive");
        Element f = ffsd.get(2);
        Elements xx = f.select("tbody");
        Elements elementsx = xx.select("img[src]");

        for (Element link : elementsx) {
            String z = link.attr("alt");
            cryptoarraynamellist.add(z);
        }
        Elements xxxd = xx.select("td[data-usd]");
        cry1 = String.valueOf(cryptoarraynamellist.get(0)) + "   " + xxxd.get(0).text();
        cry2 = String.valueOf(cryptoarraynamellist.get(1)) + "   " + xxxd.get(1).text();
        cry3 = String.valueOf(cryptoarraynamellist.get(2)) + "   " + xxxd.get(2).text();
        cry4 = String.valueOf(cryptoarraynamellist.get(3)) + "   " + xxxd.get(3).text();
        cry5 = String.valueOf(cryptoarraynamellist.get(4)) + "   " + xxxd.get(4).text();


        Element f2 = ffsd.get(5);
        Elements xx2 = f2.select("tbody");
        Elements elementsx2 = xx2.select("img[src]");
        ArrayList cryptoarraynamellist2 = new ArrayList();
        for (Element link : elementsx2) {
            String z = link.attr("alt");
            cryptoarraynamellist2.add(z);
        }
        Elements xxxd2 = xx2.select("td[data-usd]");
        cry_1 = String.valueOf(cryptoarraynamellist2.get(0)) + "   " + xxxd2.get(0).text();
        cry_2 = String.valueOf(cryptoarraynamellist2.get(1)) + "   " + xxxd2.get(1).text();
        cry_3 = String.valueOf(cryptoarraynamellist2.get(2)) + "   " + xxxd2.get(2).text();
        cry_4 = String.valueOf(cryptoarraynamellist2.get(3)) + "   " + xxxd2.get(3).text();
        cry_5 = String.valueOf(cryptoarraynamellist2.get(4)) + "   " + xxxd2.get(4).text();
        end_winlosecaps= System.currentTimeMillis();
        System.out.println(end_winlosecaps-start_winlosecaps+" Winners/Losers time in milliseconds"); //Milli Secs
        System.out.print((end_winlosecaps-start_winlosecaps)/1000); //Secs
        System.out.println(" Seconds for "+ cryptoarraynamellist.size() + " entries");

        try {
            volume_data = Jsoup.connect("https://coinmarketcap.com/currencies/volume/24-hour/").get();
            start_volume = System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i <=5; i++){
        Elements vse = volume_data.getElementsByClass("padding-top--lv6 margin-bottom--lv2");
        for (Element vlink : vse) {
            String zz = vlink.text();
            zz = zz.replaceAll("[()]", "");
            String[] split = zz.split(" ");
            cryptoarrayvolumellist.add(split[2]);
            cryptoarraynamellist.add(split[1]);
        }}

        cry_vol1 = String.valueOf(cryptoarraynamellist.get(0) + " " + cryptoarrayvolumellist.get(0));
        cry_vol2 = String.valueOf(cryptoarraynamellist.get(1) + " " + cryptoarrayvolumellist.get(1));
        cry_vol3 = String.valueOf(cryptoarraynamellist.get(2) + " " + cryptoarrayvolumellist.get(2));
        cry_vol4 = String.valueOf(cryptoarraynamellist.get(3) + " " + cryptoarrayvolumellist.get(3));
        cry_vol5 = String.valueOf(cryptoarraynamellist.get(4) + " " + cryptoarrayvolumellist.get(4));
        System.out.println(cry_vol1 + "\n" + cry_vol2 + "\n" + cry_vol3 + "\n" + cry_vol4 + "\n" + cry_vol5);
        //taco();
        end_volume= System.currentTimeMillis();
        System.out.println(end_volume-start_winlosecaps+" Volume time in milliseconds"); //Milli Secs
        System.out.print((end_volume-start_winlosecaps)/1000); //Secs
        System.out.println(" Seconds for "+ cryptoarrayvolumellist.size() + "entries");
        System.out.println("\n");
        Long mc = end_marketcaps-start_marketcaps;
        Long wl = end_winlosecaps-start_winlosecaps;
        Long cv = end_volume-start_volume;
        Long tot = mc+wl+cv;
        Long tots = tot/1000;
        System.out.println(tot+" Milliseconds to finish all");
        System.out.println(tots+" Seconds to finish all downloading for all cryptos");



// Measuring the amount of time it takes to get all data from coinmarket
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();
        Document doc = null;
        start_cmc = System.currentTimeMillis();
        String tron = "Tron";
        try {
            doc = Jsoup.connect("https://coinmarketcap.com/currencies/" + tron + "/historical-data/?start=20000101&end=" + sdf.format(begindate)).get();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements divs = doc.select("table");
        for (Element tz : divs) {
            Elements tds = tz.select("td");
            Elements s = tz.getElementsByClass("text-right");
            for (Element ss : s) {
                Elements p = ss.select("td[data-format-fiat]");
                String v = p.text();
                String[] splited = v.split("\\s+");
                if (v != null && !v.isEmpty()) {
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
        List<String> numbers = graph_high;

        _AllDays = numbers;
        _1_Day_Chart = _AllDays.subList(_AllDays.size() - 1, _AllDays.size() - 0);
        _7Days = _AllDays.subList(_AllDays.size() - 7, _AllDays.size() - 0);
        _30Days = _AllDays.subList(_AllDays.size() - 30, _AllDays.size() - 0);
        _90Days = _AllDays.subList(_AllDays.size() - 90, _AllDays.size() - 0);
        // _180Days = _AllDays.subList(_AllDays.size() - 180, _AllDays.size() - 0);
        // 1_Year_Chart = _AllDays.subList(_AllDays.size() - 365, _AllDays.size() - 0);
        end_cmc= System.currentTimeMillis();
        System.out.println(end_cmc-start_cmc+" Total download time from coinmarketcap"); //Milli Secs
        System.out.print((end_cmc-start_cmc)/1000); //Secs
    }
   /* static void taco() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a cryptocurrency");
        String chosen_crypto = scan.nextLine();

        if (cryptoarraynamellist.contains(chosen_crypto)) {
            int cry = cryptoarraynamellist.indexOf(chosen_crypto);
            String cryvolume = String.valueOf(cryptoarrayvolumellist.get(cry));
            System.out.print("This is the information for " + cryptoarraynamellist.get(cry));
            System.out.println(" ");
            System.out.println(cryvolume);
        } else {
            System.out.println("---" + chosen_crypto + "--- is not in the list");
            taco();
        }
    }
    */
}












