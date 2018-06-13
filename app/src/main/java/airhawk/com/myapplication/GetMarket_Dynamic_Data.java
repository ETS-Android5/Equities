package airhawk.com.myapplication;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static airhawk.com.myapplication.App_Variables.dow_amount;
import static airhawk.com.myapplication.App_Variables.dow_change;
import static airhawk.com.myapplication.App_Variables.dow_name;
import static airhawk.com.myapplication.App_Variables.nas_amount;
import static airhawk.com.myapplication.App_Variables.nas_change;
import static airhawk.com.myapplication.App_Variables.nas_name;
import static airhawk.com.myapplication.App_Variables.sp_amount;
import static airhawk.com.myapplication.App_Variables.sp_change;
import static airhawk.com.myapplication.App_Variables.sp_name;
import static airhawk.com.myapplication.App_Variables.sto1;
import static airhawk.com.myapplication.App_Variables.sto2;
import static airhawk.com.myapplication.App_Variables.sto3;
import static airhawk.com.myapplication.App_Variables.sto4;
import static airhawk.com.myapplication.App_Variables.sto5;
import static airhawk.com.myapplication.App_Variables.sto_1;
import static airhawk.com.myapplication.App_Variables.sto_2;
import static airhawk.com.myapplication.App_Variables.sto_3;
import static airhawk.com.myapplication.App_Variables.sto_4;
import static airhawk.com.myapplication.App_Variables.sto_5;

public class GetMarket_Dynamic_Data extends AsyncTask<Void, Void, Void> {
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
try{
                ss =Jsoup.connect("http://money.cnn.com/data/hotstocks/").get();
} catch (IOException e) {
    e.printStackTrace();
}
                Elements ddd =ss.getElementsByClass("wsod_dataTable wsod_dataTableBigAlt");
                Element ff =ddd.get(1);
                Elements x = ff.select("span");
                Elements elements = x.select("span[streamfeed]");
                sto1 = x.get(0).text() +"   "+elements.get(2).text();
                sto2 = x.get(6).text() +"   "+elements.get(8).text();
                sto3 = x.get(12).text() +"   "+elements.get(14).text();
                sto4 = x.get(18).text() +"   "+elements.get(20).text();
                sto5 = x.get(24).text() +"   "+elements.get(26).text();

                Element ff2 =ddd.get(2);
                Elements x2 = ff2.select("span");
                Elements elements2 = x2.select("span[streamfeed]");
                sto_1 = x2.get(0).text() +"   "+elements2.get(2).text();
                sto_2 = x2.get(6).text() +"   "+elements2.get(8).text();
                sto_3 = x2.get(12).text() +"   "+elements2.get(14).text();
                sto_4 = x2.get(18).text() +"   "+elements2.get(20).text();
                sto_5 = x2.get(24).text() +"   "+elements2.get(26).text();


        }}
