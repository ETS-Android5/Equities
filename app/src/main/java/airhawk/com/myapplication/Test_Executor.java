package airhawk.com.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Activity_Main.get_current_aequity_price;
import static airhawk.com.myapplication.Constructor_App_Variables._AllDays;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_low;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_market_cap;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;

public class Test_Executor {
    static String TAG = "Test_Executor";
    static long startTime = System.nanoTime();
    public static void main(String[] args) {

        String tr = "Tron";
        Constructor_App_Variables av = new Constructor_App_Variables();
        av.setMarketName(tr);
        ExecutorService service = Executors.newCachedThreadPool();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                get_nasdaq_points();
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                getVideoInfo();
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                get_crypto_points();
                return null;
            }
        });



        try {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures) {
                System.out.println(future.get());
                //Where to check all variables
                System.out.println(Arrays.asList(graph_date));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void get_nasdaq_points() {

        String marname = ap_info.getMarketSymbol();
        //int i = marname.indexOf(" ");
        //String in = marname.substring(0, i);
        //marname = in;
        List<String> name = new ArrayList<>();
        List<String> number = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://charting.nasdaq.com/ext/charts.dll?2-1-14-0-0-512-03NA000000" + marname + "-&SF:1|5-BG=FFFFFF-BT=0-HT=395--XTBL-").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements divs = doc.getElementsByClass("DrillDownData");
        Elements divsdate = doc.getElementsByClass("DrillDownDate");
        ArrayList<String> elements = new ArrayList<String>();
        for (Element el : divs) {
            Elements tds = el.select("td");
            String result = tds.get(0).text();
            elements.add(result);
        }
        ArrayList<String> elements_dates = new ArrayList<String>();
        for (Element e : divsdate) {
            Elements tdsdate = e.select("td");
            String resultdate = tdsdate.get(0).text();
            elements_dates.add(resultdate);
        }

        for (int j = elements.size() - 1; j >= 0; j--) {
            if (j % 2 == 0) { // Even
                number.add(elements.get(j));
            } else { // Odd
                name.add(elements.get(j));
            }
        }
        for (int counter = 0; counter < number.size(); counter++) {
            graph_date.add(elements_dates.get(counter));
            graph_volume.add(name.get(counter));
            graph_high.add(number.get(counter));
        }
        List<String> numbers = graph_high;
        Collections.reverse(numbers);
        _AllDays = numbers;

    }

    public static void getVideoInfo() {


        String url = "https://www.youtube.com/results";

        String begin_url;
        String mid_url;
        String end_url;
        String url_1st = "https://i.ytimg.com/vi/";
        String url_3rd = "/hqdefault.jpg";
        try {
            Document doc = Jsoup.connect(url).data("search_query", ap_info.getMarketName()).userAgent("Mozilla/5.0").timeout(10 * 10000).get();

            for (Element a : doc.select(".yt-lockup-title > a[title]")) {
                begin_url = (a.attr("href") + " " + a.attr("title"));
                begin_url = begin_url.replace("/watch?v=", "");
                mid_url = begin_url.substring(0, begin_url.indexOf(" "));
                end_url = begin_url.replace(mid_url, "");
                String f_url = url_1st + mid_url + url_3rd;
                Constructor_App_Variables.video_title.add(end_url);
                Constructor_App_Variables.video_url.add(mid_url);
                Constructor_App_Variables.image_video_url.add(f_url);
            }
stinky();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void stinky(){
        long endTime = System.nanoTime();
        long MethodeDuration = (endTime - startTime);
        System.out.println("TIME "+MethodeDuration/1000000000+" seconds");
    }

    public static void get_crypto_points() {
        Element price;
        Document doc = null;
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();

        //market_name="tron";
        try {
            doc = Jsoup.connect("https://coinmarketcap.com/currencies/" + ap_info.getMarketName() + "/historical-data/?start=20000101&end=" + sdf.format(begindate)).timeout(10 * 10000).get();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }


        price = doc.getElementById("quote_price");
        String v = price.text();
        String[] splitz = v.split(" ");
        double d = Double.parseDouble(splitz[0]);
        DecimalFormat df = new DecimalFormat("#0.000");
        get_current_aequity_price = df.format(d);
        Elements divs = doc.select("table");
        for (Element tz : divs) {
            Elements tds = tz.select("td");
            Elements s = tz.getElementsByClass("text-right");
            for (Element ss : s) {
                Elements p = ss.select("td[data-format-fiat]");
                String g = p.text();
                String[] splited = g.split("\\s+");
                if (v != null && !g.isEmpty()) {
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
        for (int counter = 0; counter < numbers.size(); counter++) {
            System.out.println("VOLUME " + graph_volume.get(counter) + " NUMBER " + graph_high.get(counter) + " DATE " + graph_date.get(counter));

        }


    }

}
