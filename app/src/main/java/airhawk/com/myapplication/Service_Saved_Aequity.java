package airhawk.com.myapplication;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables._AllDays;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_low;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_market_cap;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;

//The original version of this code was found on StackOverflow.com from user flup
//https://stackoverflow.com/users/1973271/flup
//https://stackoverflow.com/questions/21670451/how-to-send-multiple-asynchronous-requests-to-different-web-services


public class Service_Saved_Aequity

{
    public static Context myContext;
    static ArrayList temp =new ArrayList();
    public Service_Saved_Aequity(Context context){

        this.myContext=context;

    }

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
                get_saved_price();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //29 seconds Boost Mobile
                System.out.println("GET get_saved_price TIME IS "+duration/1000000000+" seconds");
                return null;
            }
        });

        callables.add(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                long startTime = System.nanoTime();
                get_crypto_saved_price();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                //29 seconds Boost Mobile
                System.out.println("GET get_saved_price TIME IS "+duration/1000000000+" seconds");
                return null;
            }
        });


        try
        {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures)
            {
                //System.out.println (future.get());
                //Where to check all variables
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    public static void get_saved_price(){
        Database_Local_Aequities co =new Database_Local_Aequities(myContext.getApplicationContext());
        Constructor_App_Variables cav =new Constructor_App_Variables();
        ArrayList a = co.getSymbol();
        ArrayList aa = co.getType();
        ArrayList b = co.getstockSymbol();
        for(int i=0;i<a.size();i++){
            Document cap =null;
            if(aa.get(i).equals("Stock")) {
                try {
                    cap = Jsoup.connect("https://finance.yahoo.com/quote/" + b.get(i)).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Elements cc = cap.select("div>span");
                temp.add(cc.get(8).text());
                System.out.println("It's a stock "+cc.get(8).text());
            }
            cav.setCpr(temp);

        }


    }

    public static void get_crypto_saved_price(){
        Database_Local_Aequities co =new Database_Local_Aequities(myContext.getApplicationContext());
        Constructor_App_Variables cav =new Constructor_App_Variables();


        ArrayList d = co.getcryptoName();
        for(int i=0;i<d.size();i++){
            Document cap =null;
                String g = String.valueOf(d.get(i));
                if(g.contains(" ")){
                    g= g.replace(" ","-");
                }
                try {
                    cap = Jsoup.connect("https://coinmarketcap.com/currencies/" + g).timeout(10 *10000).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements as = cap.select("div>span");
                String f =as.get(4).text();
                f= f.replaceAll("\\(","").replaceAll("\\)","");
                System.out.println("It's a crypto "+f);
                temp.add(f);
            }







    }

    public static void get_crypto_points() {
        Element price;
        Document doc = null;
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();

       String f = ap_info.getMarketName();
        System.out.println("SYMBO NAME "+f);
       if (f.contains(" ")){
       f= f.replaceAll(" ","-");}

        try {
            doc = Jsoup.connect("https://coinmarketcap.com/currencies/" + f + "/historical-data/?start=20000101&end=" + sdf.format(begindate)).timeout(10 * 1000).get();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }


        String b= "B";
        String m = "M";
        Elements a = doc.select("span[data-currency-value]");
        int counter = a.get(1).text().split("\\,", -1).length - 1;
        String aa =a.get(1).text().replaceFirst(",",".");
        aa =aa.replace(",","");
        aa =aa.substring(0,6);
        if (counter==3){
            ap_info.setMarketCap(aa+" "+b);}
        if (counter==2){
            ap_info.setMarketCap(aa+" "+m);
        }

        Elements dd = doc.select("span[data-format-supply]");
        ap_info.setMarketSupply(dd.get(1).text());


        price = doc.getElementById("quote_price");
        Elements vv = doc.select("span[data-format-percentage]");
        String [] flit = vv.text().split(" ");
        System.out.println("QUOTE PRICE  "+vv.text());

        ap_info.setCurrent_Aequity_Price_Change(flit[0]+" %");
        String v = price.text();
        String[] splitz = v.split(" ");
        double d = Double.parseDouble(splitz[0]);
        DecimalFormat df = new DecimalFormat("#0.000");
        ap_info.setCurrent_Aequity_Price(df.format(d));
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

}
