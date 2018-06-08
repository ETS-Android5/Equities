package airhawk.com.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static airhawk.com.myapplication.Activity_Main.market_name;


public class Test_Crypto_Graph_Points {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {

        List<String> graph_date = new ArrayList<>();
        List<String> graph_high = new ArrayList<>();
        List<String> graph_low = new ArrayList<>();
        List<String> graph_volume = new ArrayList<>();
        List<String> graph_market_cap = new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();
        System.out.println(sdf.format(begindate));
        String url ="https://coinmarketcap.com/currencies/bitcoin/historical-data/?start=19800518&end="+sdf.format(begindate);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements divs = doc.select("table");
        for (Element tz :divs) {
            Elements tds = tz.select("td");
            Elements s = tz.getElementsByClass("text-right");
            for (Element ss :s) {
                Elements p = ss.select("td[data-format-fiat]");
                String v = p.text();
                String[] splited = v.split("\\s+");
                if (v != null && !v.isEmpty()){
                    graph_high.add(splited[1]);
                    graph_low.add(splited[2]);
                    }
                Elements pn = ss.select("td[data-format-market-cap]");
                String vp = pn.text();
                String[] split = vp.split("\\s+");
                if (vp != null && !vp.isEmpty()){
                    graph_volume.add(split[0]);
                    graph_market_cap.add(split[1]);}
            }
            for (Element bb :tds) {
                Elements gdate = bb.getElementsByClass("text-left");
                String result0 = gdate.text();
                if (result0 != null && !result0.isEmpty()){
                graph_date.add(String.valueOf(result0));}
            }}


        List<String> numbers = graph_high;
        //Collections.reverse(numbers);
        List<String> _7Days = numbers.subList(0, 7);
        List<String> _30days = numbers.subList(0, 30);
        List<String> _90days = numbers.subList(0, 90);
        List<String> _180days = numbers.subList(0, 180);

        System.out.println("7 DAY "+_7Days);
        System.out.println("30 DAYS "+_30days);
        System.out.println("90 DAYS "+_90days);
        System.out.println("180 DAYS"+_180days);
        System.out.println("ALL DAYS"+numbers);
            for (int i = 0; i < graph_date.size(); i++) {
            System.out.println("DATE "+graph_date.get(i));
            }
        for (int i = 0; i < graph_high.size(); i++) {
            System.out.println("HIGH "+graph_high.get(i));
        }
        for (int i = 0; i < graph_low.size(); i++) {
            System.out.println("LOW "+graph_low.get(i));
        }
        for (int i = 0; i < graph_volume.size(); i++) {
            System.out.println("VOLUME "+graph_volume.get(i));
        }
        for (int i = 0; i < graph_market_cap.size(); i++) {
            System.out.println("MARKET CAP "+graph_market_cap.get(i));
        }

}}









