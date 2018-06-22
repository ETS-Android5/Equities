package airhawk.com.myapplication;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static airhawk.com.myapplication.App_Variables.all_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.alt_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.btc_market_cap_change;


public class GetCrypto_Dynamic_Data extends AsyncTask<Void, Void, Void> {
    static ArrayList cryptochangelist = new ArrayList();
    static ArrayList cryptonamelist = new ArrayList();
    static ArrayList cryptosymbollist = new ArrayList();
        @Override
        protected Void doInBackground(Void... voids) {
            crypto();
            return null;
        }
        public void crypto() {
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
            Elements xxxd = xx.select("td[data-usd]");//Get's percentage change
            Elements ax = xx.select("tr");
            Elements name_change = xx.select("img[src]");
            for (Element crypto_symbol : ax)
            {
                String symbol = crypto_symbol.select("td.text-left").text();
                cryptosymbollist.add(symbol);
            }
            for (Element crypto_name : name_change)
            {
                String name = crypto_name.attr("alt");
                cryptonamelist.add(name);
            }
            for (Element crypto_change : xxxd)
            {
                cryptochangelist.add(crypto_change.text());
            }
}}
