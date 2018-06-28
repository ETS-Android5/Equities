package airhawk.com.myapplication;

import android.content.Context;
import android.text.Html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Activity_Main.get_current_aequity_price;
import static airhawk.com.myapplication.Constructor_App_Variables._AllDays;
import static airhawk.com.myapplication.Constructor_App_Variables.feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_low;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_market_cap;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;

//The original version of this code was found on StackOverflow.com from user flup
//https://stackoverflow.com/users/1973271/flup
//https://stackoverflow.com/questions/21670451/how-to-send-multiple-asynchronous-requests-to-different-web-services


public class Executor_Chosen_Aequity
{

    public static void main()
    {

        ExecutorService service = Executors.newCachedThreadPool();
        Set <Callable<String>> callables = new HashSet <Callable<String>> ();
        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                long startTime = System.nanoTime();
                Constructor_App_Variables ax = new Constructor_App_Variables();
                String a = ax.getMarketType();
                System.out.println("This is what a actually equals "+a);
                if (a.equals("Cryptocurrency"))
                {
                    get_crypto_points();
                }else
                    {
                    get_nasdaq_points();
                    }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("GET get_crypto_points/get_nasdaq_points TIME IS "+duration/1000000000+" seconds");
                return null;
            }
        });

        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {long startTime = System.nanoTime();
                getVideoInfo();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("GET VIDEOS TIME IS "+duration/1000000000+" seconds");
                return null;
            }
        });

        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {long startTime = System.nanoTime();
                ProcessXml(GoogleRSFeed());
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("GET NEWS TIME IS "+duration/1000000000+" seconds");
                return null;
            }
        });


        try
        {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures)
            {
                System.out.println (future.get());
                //Where to check all variables
                System.out.println(Arrays.asList(graph_date));
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
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

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //THIS METHOD IS TOO SLOW
    public static void get_crypto_points() {
        Element price;
        Document doc = null;
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();

        //market_name="tron";
        try {
            doc = Jsoup.connect("https://coinmarketcap.com/currencies/" + ap_info.getMarketName() + "/historical-data/?start=20000101&end=" + sdf.format(begindate)).timeout(10 * 1000).get();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }




        price = doc.getElementById("quote_price");
        String v = price.text();
        String[] splitz = v.split(" ");
        double d = Double.parseDouble(splitz[0]);
        DecimalFormat df = new DecimalFormat("#0.000");
        get_current_aequity_price =df.format(d);
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
        }_AllDays = graph_high;
        //for (int counter = 0; counter < numbers.size(); counter++) {
       //     System.out.println("VOLUME " + graph_volume.get(counter) + " NUMBER " + graph_high.get(counter) + " DATE " + graph_date.get(counter));

        //}


    }
    private static void ProcessXml(org.w3c.dom.Document data) {
        if (data != null) {
            String st, sd, sp, sl;
            org.w3c.dom.Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(0);
            NodeList items = channel.getChildNodes();

            for (int i = 0; i < items.getLength(); i++) {
                Constructor_News_Feed it = new Constructor_News_Feed();
                Node curentchild = items.item(i);
                if (curentchild.getNodeName().equalsIgnoreCase("item")) {
                    NodeList itemchilds = curentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node curent = itemchilds.item(j);
                        if (curent.getNodeName().equalsIgnoreCase("title")) {
                            st = Html.fromHtml(curent.getTextContent()).toString();
                            it.setTitle(st);
                        } else if (curent.getNodeName().equalsIgnoreCase("description")) {
                            sd = Html.fromHtml(curent.getTextContent()).toString();
                            String d = curent.getTextContent().toString();
                            String pattern1 = "<img src=\"";
                            String pattern2 = "\"";
                            Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
                            Matcher m = p.matcher(d);
                            while (m.find()) {
                                it.setThumbnailUrl(m.group(1));
                            }
                            it.setDescription(sd);

                        } else if (curent.getNodeName().equalsIgnoreCase("pubDate")) {
                            sp = Html.fromHtml(curent.getTextContent()).toString();
                            sp = sp.replaceAll("@20", " ");
                            it.setPubDate(sp);
                        } else if (curent.getNodeName().equalsIgnoreCase("link")) {
                            sl = Html.fromHtml(curent.getTextContent()).toString();
                            sl = sl.replaceAll("@20", " ");
                            it.setLink(sl);
                        } else if (curent.getNodeName().equalsIgnoreCase("img src")) {

                        }
                    }

                    feedItems.add(it);


                }
            }
        }
    }
    public static org.w3c.dom.Document GoogleRSFeed() {
        try {
            URL url;
            Context context;
            String repo;
            repo = ap_info.getMarketName();
            repo = repo.replace("  ", " ");
            System.out.println("BIG BOOBS1 " + repo);
            if (repo.contains(" ")) {
                String remove = repo.substring(repo.lastIndexOf(" "));
                repo = repo.replace(remove, "");
            }
            repo = repo.replace(" ", "%20");
            String address = "https://news.google.com/news/rss/search/section/q/" + repo + "?ned=us&gl=US&hl=en";

            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
