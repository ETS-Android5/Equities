package airhawk.com.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
public class TestMethods {
    public static void main(String[] args) {


        Document volume_data = null;
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
        Elements ffsd = stock_data.getElementsByClass("table-responsive");
        Element f = ffsd.get(2);
        Elements xx = f.select("tbody");
        Elements elementsx = xx.select("img[src]");
        ArrayList cryptoarraynamellist = new ArrayList();
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


        try {
            volume_data = Jsoup.connect("https://coinmarketcap.com/currencies/volume/24-hour/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList cryptoarrayvolumellist = new ArrayList();

        Elements vse = volume_data.getElementsByClass("padding-top--lv6 margin-bottom--lv2");
        for (Element vlink : vse) {
            String zz = vlink.text();
            zz = zz.replaceAll("[()]","");
            System.out.println(zz.indexOf(' '));
            cryptoarrayvolumellist.add(zz);
        }

        cry_vol1 = String.valueOf(cryptoarrayvolumellist.get(0));
        cry_vol2 = String.valueOf(cryptoarrayvolumellist.get(1));
        cry_vol3 = String.valueOf(cryptoarrayvolumellist.get(2));
        cry_vol4 = String.valueOf(cryptoarrayvolumellist.get(3));
        cry_vol5 = String.valueOf(cryptoarrayvolumellist.get(4));
        System.out.println(Arrays.asList(cryptoarrayvolumellist));
    }




}









