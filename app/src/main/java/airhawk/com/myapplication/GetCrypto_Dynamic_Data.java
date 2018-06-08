package airhawk.com.myapplication;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static airhawk.com.myapplication.Activity_Main.all_market_cap_amount;
import static airhawk.com.myapplication.Activity_Main.alt_market_cap_amount;
import static airhawk.com.myapplication.Activity_Main.btc_market_cap_amount;
import static airhawk.com.myapplication.Activity_Main.btc_market_cap_change;
import static airhawk.com.myapplication.Activity_Main.cry1;
import static airhawk.com.myapplication.Activity_Main.cry2;
import static airhawk.com.myapplication.Activity_Main.cry3;
import static airhawk.com.myapplication.Activity_Main.cry4;
import static airhawk.com.myapplication.Activity_Main.cry5;
import static airhawk.com.myapplication.Activity_Main.cry_1;
import static airhawk.com.myapplication.Activity_Main.cry_2;
import static airhawk.com.myapplication.Activity_Main.cry_3;
import static airhawk.com.myapplication.Activity_Main.cry_4;
import static airhawk.com.myapplication.Activity_Main.cry_5;
import static airhawk.com.myapplication.Activity_Main.cry_vol1;
import static airhawk.com.myapplication.Activity_Main.cry_vol2;
import static airhawk.com.myapplication.Activity_Main.cry_vol3;
import static airhawk.com.myapplication.Activity_Main.cry_vol4;
import static airhawk.com.myapplication.Activity_Main.cry_vol5;


public class GetCrypto_Dynamic_Data extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            crypto();
            return null;
        }
        public void crypto() {

Document volume_data =null;
Document stock_data =null;
Document crypto_data =null;
            try {
    crypto_data = Jsoup.connect("https://www.worldcoinindex.com/").timeout(10*10000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
    Elements crypto_content = crypto_data.getElementsByClass("top-marketcap");
    String cryptext = crypto_content.text();
    String [] cryptarray = cryptext.split(" ");
    all_market_cap_amount = cryptarray[4];

                Elements btc_mc = crypto_data.getElementsByClass("row row-body zoomer");
                Elements xxdd = btc_mc.select("tbody");
                Elements cc = xxdd.select("span");
                String c =cc.text();
                String ce [] = c.split(" ");
                btc_market_cap_change =ce[4];
                String are =xxdd.text();
                String aren [] =are.split(" ");
                String ca =cryptarray[4].replace("B","");
                String ba =aren[14].replace("B","");
                Double dca =Double.parseDouble(ca);
                Double dba =Double.parseDouble(ba);
                Double dcadba =dca-dba;
                Float t = dcadba.floatValue();
                String altmc =t.toString();
                btc_market_cap_amount =aren[14];
                alt_market_cap_amount =altmc+"B";
try{

                stock_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").get();
} catch (IOException e) {
    e.printStackTrace();
}
                Elements ffsd =stock_data.getElementsByClass("table-responsive");
                Element f =ffsd.get(2);
                Elements xx = f.select("tbody");
                Elements elementsx = xx.select("img[src]");
                ArrayList cryptoarraynamellist =new ArrayList();
                for (Element link : elementsx){
                    String z =link.attr("alt");
                    cryptoarraynamellist.add(z);
                }
                Elements xxxd =xx.select("td[data-usd]");
                cry1 = String.valueOf(cryptoarraynamellist.get(0))+"   "+xxxd.get(0).text();
                cry2 = String.valueOf(cryptoarraynamellist.get(1))+"   "+xxxd.get(1).text();
                cry3 = String.valueOf(cryptoarraynamellist.get(2))+"   "+xxxd.get(2).text();
                cry4 = String.valueOf(cryptoarraynamellist.get(3))+"   "+xxxd.get(3).text();
                cry5 = String.valueOf(cryptoarraynamellist.get(4))+"   "+xxxd.get(4).text();


                Element f2 =ffsd.get(5);
                Elements xx2 = f2.select("tbody");
                Elements elementsx2 = xx2.select("img[src]");
                ArrayList cryptoarraynamellist2 =new ArrayList();
                for (Element link : elementsx2){
                    String z =link.attr("alt");
                    cryptoarraynamellist2.add(z);
                }
                Elements xxxd2 =xx2.select("td[data-usd]");
                cry_1 = String.valueOf(cryptoarraynamellist2.get(0))+"   "+xxxd2.get(0).text();
                cry_2 = String.valueOf(cryptoarraynamellist2.get(1))+"   "+xxxd2.get(1).text();
                cry_3 = String.valueOf(cryptoarraynamellist2.get(2))+"   "+xxxd2.get(2).text();
                cry_4 = String.valueOf(cryptoarraynamellist2.get(3))+"   "+xxxd2.get(3).text();
                cry_5 = String.valueOf(cryptoarraynamellist2.get(4))+"   "+xxxd2.get(4).text();


try{
                volume_data = Jsoup.connect("https://coinmarketcap.com/currencies/volume/24-hour/").get();
} catch (IOException e) {
    e.printStackTrace();
}
                Elements vsd =volume_data.getElementsByClass("volume-header");
                Elements vse = vsd.select("a[href]");
                Elements elx =volume_data.getElementsByClass("bold text-right volume");
                ArrayList cryptoarrayvolumellist =new ArrayList();
                String z =null;
                String p =null;
                String z1 =null;
                String p1 =null;
                String z2 =null;
                String p2 =null;
                String z3 =null;
                String p3 =null;
                String z4 =null;
                String p4 =null;

                for (Element vlink : vse) {
                    z = vse.get(0).text();
                    p = elx.get(0).text();
                    z1 = vse.get(1).text();
                    p1 = elx.get(1).text();
                    z2 = vse.get(2).text();
                    p2 = elx.get(2).text();
                    z3 = vse.get(3).text();
                    p3 = elx.get(3).text();
                    z4 = vse.get(4).text();
                    p4 = elx.get(4).text();
                    cryptoarrayvolumellist.add(z+" "+p);
                    cryptoarrayvolumellist.add(z1+" "+p1);
                    cryptoarrayvolumellist.add(z2+" "+p2);
                    cryptoarrayvolumellist.add(z3+" "+p3);
                    cryptoarrayvolumellist.add(z4+" "+p4);


                }
                cry_vol1 = String.valueOf(cryptoarrayvolumellist.get(0));
                cry_vol2 = String.valueOf(cryptoarrayvolumellist.get(1));
                cry_vol3 = String.valueOf(cryptoarrayvolumellist.get(2));
                cry_vol4 = String.valueOf(cryptoarrayvolumellist.get(3));
                cry_vol5 = String.valueOf(cryptoarrayvolumellist.get(4));

        }}

