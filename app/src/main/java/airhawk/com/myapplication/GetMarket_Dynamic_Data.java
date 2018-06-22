package airhawk.com.myapplication;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static airhawk.com.myapplication.App_Variables.dow_amount;
import static airhawk.com.myapplication.App_Variables.dow_change;
import static airhawk.com.myapplication.App_Variables.dow_name;
import static airhawk.com.myapplication.App_Variables.nas_amount;
import static airhawk.com.myapplication.App_Variables.nas_change;
import static airhawk.com.myapplication.App_Variables.nas_name;
import static airhawk.com.myapplication.App_Variables.sp_amount;
import static airhawk.com.myapplication.App_Variables.sp_change;
import static airhawk.com.myapplication.App_Variables.sp_name;

public class GetMarket_Dynamic_Data extends AsyncTask<Void, Void, Void> {
    static ArrayList stock_win_symbol = new ArrayList();
    static ArrayList stock_win_name = new ArrayList();
    public static ArrayList stock_win_change = new ArrayList();
        @Override
        protected Void doInBackground(Void... voids) {
            ret();
            return null;
        }
        public void ret() {


            Document nas_data = null;
            Document sp_data = null;
            Document dow_data = null;
            Document ss =null;
            try {
                dow_data = Jsoup.connect("https://www.marketwatch.com/investing/index/djia").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
                Elements dowtext = dow_data.getElementsByClass("intraday__price");
                String dt = dowtext.text();
                String dn = String.valueOf(dt);

                Elements dowchange = dow_data.getElementsByClass("change--percent--q");
                String dowcp = dowchange.text();
                String dcp = String.valueOf(dowcp);

                dow_name ="Dow Jones";
                dow_amount =dn;
                dow_change =dcp;
try{
                sp_data = Jsoup.connect("https://www.marketwatch.com/investing/index/spx").get();
} catch (IOException e) {
    e.printStackTrace();
}
                Elements sptext = sp_data.getElementsByClass("intraday__price");
                String s = sptext.text();
                String sp = String.valueOf(s);

                Elements spchange = sp_data.getElementsByClass("change--percent--q");
                String spp = spchange.text();
                String sc = String.valueOf(spp);

                sp_name = "S&P 500";
                sp_amount =sp;
                sp_change =sc;

try{
                nas_data = Jsoup.connect("https://www.marketwatch.com/investing/index/comp").get();
} catch (IOException e) {
    e.printStackTrace();
}
                Elements nastext = nas_data.getElementsByClass("intraday__price");
                String nas = nastext.text();
                String nasp = String.valueOf(nas);

                Elements naschange = nas_data.getElementsByClass("change--percent--q");
                String naspp = naschange.text();
                String nasc = String.valueOf(naspp);

                nas_name ="Nasdaq";
                nas_amount =nasp;
                nas_change =nasc;


            try {
                ss = Jsoup.connect("http://money.cnn.com/data/hotstocks/").get();
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
                stock_win_symbol.add(symbol);

            }
            for (Element stock_name : b)
            {
                String name = stock_name.text();
                if (name.isEmpty()){}
                else
                {
                    stock_win_name.add(name);
                }
            }
            for (Element stock_change : aa)
            {
                String change = stock_change.text();
                if (change.isEmpty()){}
                else
                {
                    if(change.contains("%")){
                        stock_win_change.add(change);
                    }

                }
            }
}
        }
